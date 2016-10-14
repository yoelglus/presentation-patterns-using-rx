package com.yoelglus.presentation.patterns.mvpvm;

import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.model.ItemDetailViewModel;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Subscription;

public class MvpVmItemDetailsPresenter extends MvpVmAbstractPresenter<ItemDetailViewModel> {

    private GetItem mGetItem;
    private Subscription mGetItemSubscription;

    public MvpVmItemDetailsPresenter(GetItem getItem) {
        mGetItem = getItem;
    }

    @Override
    protected void onUnSubscribe() {
        super.onUnSubscribe();
        mGetItemSubscription.unsubscribe();
    }

    void loadItem(String itemId) {
        mGetItemSubscription = mGetItem.execute(itemId)
                .map(ItemModel::from)
                .map(ItemDetailViewModel::new)
                .subscribe(this::notifyOnChange);
    }

}
