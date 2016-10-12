package com.yoelglus.presentation.patterns.mvp;


import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import rx.Subscription;
import rx.functions.Action1;

public class MvpItemDetailsPresenter extends AbstractPresenter<MvpItemDetailsPresenter.View> {

    private GetItem mGetItem;
    private Subscription mGetItemSubscription;

    public MvpItemDetailsPresenter(GetItem getItem) {
        mGetItem = getItem;
    }


    @Override
    public void onTakeView() {
        mGetItemSubscription = mGetItem.execute().subscribe(new Action1<Item>() {
            @Override
            public void call(Item item) {
                mView.showItem(ItemModel.from(item));
            }
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
