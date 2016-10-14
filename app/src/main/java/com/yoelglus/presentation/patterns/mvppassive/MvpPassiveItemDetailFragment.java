package com.yoelglus.presentation.patterns.mvppassive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.memoizrlabs.Shank;
import com.yoelglus.presentation.patterns.R;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Observable;
import rx.subjects.PublishSubject;

import static rx.subjects.PublishSubject.create;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link MvpPassiveItemListActivity}
 * in two-pane mode (on tablets) or a {@link MvpPassiveItemDetailActivity}
 * on handsets.
 */
public class MvpPassiveItemDetailFragment extends Fragment implements MvpPassiveItemDetailsPresenter.View {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private MvpPassiveItemDetailsPresenter mPresenter;
    private TextView mItemDetail;
    private PublishSubject<String> mLoadItemSubject = create();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MvpPassiveItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = Shank.provideNew(MvpPassiveItemDetailsPresenter.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        mItemDetail = (TextView) rootView.findViewById(R.id.item_detail);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.takeView(this);
        mLoadItemSubject.onNext(getArguments().getString(ARG_ITEM_ID));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView(this);
    }

    @Override
    public void showItem(ItemModel itemModel) {
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(itemModel.getContent());
        }
        mItemDetail.setText(itemModel.getDetail());
    }

    @Override
    public Observable<String> loadItem() {
        return mLoadItemSubject;
    }
}
