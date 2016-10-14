package com.yoelglus.presentation.patterns.mvc;

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

import rx.Subscription;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link MvcItemListActivity}
 * in two-pane mode (on tablets) or a {@link MvcItemDetailActivity}
 * on handsets.
 */
public class MvcItemDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private MvcItemDetailsController mController;
    private MvcItemDetailsModel mModel;
    private TextView mItemDetail;
    private Subscription mModelSubscription;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MvcItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = Shank.provideNew(MvcItemDetailsController.class);
        mModel = Shank.provideSingleton(MvcItemDetailsModel.class);
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
        mModelSubscription = mModel.itemDetails().subscribe(item -> showItem(ItemModel.from(item)));
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mController.loadItemDetails(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mModelSubscription.unsubscribe();
    }

    private void showItem(ItemModel itemModel) {
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(itemModel.getContent());
        }
        mItemDetail.setText(itemModel.getDetail());
    }
}
