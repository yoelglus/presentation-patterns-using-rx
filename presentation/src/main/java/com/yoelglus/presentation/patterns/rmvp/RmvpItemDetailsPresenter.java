package com.yoelglus.presentation.patterns.rmvp;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Subscription;
import rx.functions.Action1;

public class RmvpItemDetailsPresenter extends AbstractPresenter<RmvpItemDetailsPresenter.View> {

    private ItemsRepository mItemsRepository;
    private Subscription mGetItemSubscription;
    private String mItemId;

    public RmvpItemDetailsPresenter(ItemsRepository itemsRepository, String itemId) {
        mItemsRepository = itemsRepository;
        mItemId = itemId;
    }


    @Override
    public void onTakeView() {
        mGetItemSubscription = mItemsRepository.getItem(mItemId).map(ItemModel::from).subscribe(mView.showItem());
    }

    @Override
    public void onDropView() {
        mGetItemSubscription.unsubscribe();
    }

    public interface View {
        Action1<ItemModel> showItem();
    }
}
