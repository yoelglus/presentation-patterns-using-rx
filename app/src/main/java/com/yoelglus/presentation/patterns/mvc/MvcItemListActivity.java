package com.yoelglus.presentation.patterns.mvc;

import com.jakewharton.rxbinding.view.RxView;
import com.memoizrlabs.Shank;
import com.yoelglus.presentation.patterns.R;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rx.Subscription;

import static java.util.Collections.emptyList;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MvcItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MvcItemListActivity extends AppCompatActivity {

    private SimpleItemRecyclerViewAdapter mAdapter;
    private MvcItemsListController mController;
    private MvcItemsListModel mModel;
    private Subscription mModelSubscription;
    private ItemModelsMapper mItemModelsMapper = new ItemModelsMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean twoPane = findViewById(R.id.item_detail_container) != null;
        mController = Shank.provideNew(MvcItemsListController.class, this, twoPane);
        mModel = Shank.provideSingleton(MvcItemsListModel.class);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        RxView.clicks(findViewById(R.id.fab)).subscribe(mController.addItemClicks());

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mModelSubscription = mModel.itemsList().subscribe(itemsList -> showItems(mItemModelsMapper.map(itemsList)));
        mController.loadItemsList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mModelSubscription.unsubscribe();
    }

    private void showItems(List<ItemModel> itemModels) {
        mAdapter.setValues(itemModels);
        mAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<ItemModel> mValues = emptyList();

        void setValues(List<ItemModel> values) {
            mValues = values;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getId());
            holder.mContentView.setText(mValues.get(position).getContent());
            RxView.clicks(holder.mView).map(aVoid -> holder.mItem.getId()).subscribe(mController.itemClicked());
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final View mView;
            private final TextView mIdView;
            private final TextView mContentView;
            private ItemModel mItem;

            ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

}
