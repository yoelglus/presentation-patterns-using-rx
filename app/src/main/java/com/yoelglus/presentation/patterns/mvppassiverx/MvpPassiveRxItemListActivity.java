package com.yoelglus.presentation.patterns.mvppassiverx;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

import static java.util.Collections.emptyList;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MvpPassiveRxItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MvpPassiveRxItemListActivity extends AppCompatActivity implements MvpPassiveRxItemsListPresenter.View {

    private SimpleItemRecyclerViewAdapter mAdapter;
    private MvpPassiveRxItemsListPresenter mPresenter;
    private PublishSubject<Void> mAddItemClicks = PublishSubject.create();
    private PublishSubject<String> mItemClicks = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean twoPane = findViewById(R.id.item_detail_container) != null;
        mPresenter = Shank.provideNew(MvpPassiveRxItemsListPresenter.class, this, twoPane);

        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddItemClicks.onNext(null);
            }
        });

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.takeView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.dropView(this);
    }

    @Override
    public Action1<List<ItemModel>> showItems() {
        return new Action1<List<ItemModel>>() {
            @Override
            public void call(List<ItemModel> itemModels) {
                mAdapter.setValues(itemModels);
                mAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    public Observable<Void> addItemClicks() {
        return mAddItemClicks;
    }

    @Override
    public Observable<String> itemClicks() {
        return mItemClicks;
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

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClicks.onNext(holder.mItem.getId());
                }
            });
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
