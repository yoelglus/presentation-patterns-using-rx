package com.yoelglus.presentation.patterns.presentation.presenter;

import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.presentation.model.ItemModel;

import rx.Subscription;
import rx.functions.Action1;

public class ItemDetailsPresenter extends AbstractPresenter<ItemDetailsPresenter.View> {

    private GetItem mGetItem;
    private Subscription mGetItemSubscription;

    public ItemDetailsPresenter(GetItem getItem) {
        mGetItem = getItem;
    }


    @Override
    void onTakeView() {
        mGetItemSubscription = mGetItem.execute().subscribe(new Action1<Item>() {
            @Override
            public void call(Item item) {
                mView.showItem(ItemModel.from(item));
            }
        });
    }

    @Override
    void onDropView() {
        mGetItemSubscription.unsubscribe();
    }

    public interface View {
        void showItem(ItemModel itemModel);
    }
}
