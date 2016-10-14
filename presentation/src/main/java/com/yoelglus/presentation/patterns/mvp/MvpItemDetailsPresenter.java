package com.yoelglus.presentation.patterns.mvp;


import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
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
    }

    @Override
    public void onDropView() {
        mGetItemSubscription.unsubscribe();
    }

    Action1<String> loadItem() {
        return itemId -> mGetItemSubscription = mGetItem.execute(itemId)
                .map(ItemModel::from)
                .subscribe(mView.showItem());
    }

    public interface View {
        Action1<ItemModel> showItem();
    }
}
