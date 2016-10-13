package com.yoelglus.presentation.patterns.mvp;


import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import rx.Subscription;

public class MvpItemDetailsPresenter extends AbstractPresenter<MvpItemDetailsPresenter.View> {

    private GetItem mGetItem;
    private Subscription mGetItemSubscription;

    public MvpItemDetailsPresenter(GetItem getItem) {
        mGetItem = getItem;
    }


    @Override
    public void onTakeView() {
        mGetItemSubscription = mGetItem.execute().subscribe(item -> {
            mView.showItem(ItemModel.from(item));
        });
    }

    @Override
    public void onDropView() {
        mGetItemSubscription.unsubscribe();
    }

    public interface View {
        void showItem(ItemModel itemModel);
    }
}
