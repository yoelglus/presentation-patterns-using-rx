package com.yoelglus.presentation.patterns.mvvm;

import com.jakewharton.rxbinding.view.RxView;
import com.memoizrlabs.Shank;
import com.yoelglus.presentation.patterns.R;
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

import rx.internal.util.SubscriptionList;

import static java.util.Collections.emptyList;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MvvmItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MvvmItemListActivity extends AppCompatActivity {

    private SimpleItemRecyclerViewAdapter adapter;
    private ItemsListViewModel viewModel;
    private SubscriptionList subscriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = Shank.provideNew(ItemsListViewModel.class, this);

        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        subscriptionList = new SubscriptionList();
        subscriptionList.add(RxView.clicks(findViewById(R.id.fab)).subscribe(aVoid -> viewModel.addItemClicked()));

        subscriptionList.add(viewModel.itemModels().subscribe(this::showItems));

    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptionList.unsubscribe();
    }

    private void showItems(List<ItemModel> itemModels) {
        adapter.setValues(itemModels);
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
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
            RxView.clicks(holder.mView).map(aVoid -> holder.mItem.getId()).subscribe(viewModel::itemClicked);
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
