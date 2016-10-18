package com.yoelglus.presentation.patterns.mvvm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.memoizrlabs.Shank;
import com.yoelglus.presentation.patterns.R;
import com.yoelglus.presentation.patterns.model.ItemModel;

import java.util.List;

import rx.internal.util.SubscriptionList;

import static com.jakewharton.rxbinding.view.RxView.clicks;
import static java.util.Collections.emptyList;

public class MvvmItemListActivity extends AppCompatActivity {

    private SimpleItemRecyclerViewAdapter adapter;
    private ItemsListViewModel viewModel;
    private SubscriptionList subscriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = Shank.provideNew(ItemsListViewModel.class, this);

        setContentView(R.layout.activity_item_list);

        setUpToolbar();

        setupRecyclerView((RecyclerView) findViewById(R.id.item_list));

        subscriptionList = new SubscriptionList();
        subscriptionList.add(clicks(findViewById(R.id.fab)).subscribe(aVoid -> viewModel.addItemClicked()));
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

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
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
            ItemModel itemModel = mValues.get(position);
            holder.mIdView.setText(itemModel.getId());
            holder.mContentView.setText(itemModel.getContent());
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView mIdView;
            private final TextView mContentView;

            ViewHolder(View view) {
                super(view);
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
