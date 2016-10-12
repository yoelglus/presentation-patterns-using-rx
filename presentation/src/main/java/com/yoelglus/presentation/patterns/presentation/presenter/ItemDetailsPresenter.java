package com.yoelglus.presentation.patterns.presentation.presenter;

import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.presentation.model.ItemModel;

import rx.Subscriber;
import rx.Subscription;

public class ItemDetailsPresenter extends AbstractPresenter<ItemDetailsPresenter.View> {

    private GetItem mGetItem;
    private Subscription mGetItemSubscription;

    public ItemDetailsPresenter(GetItem getItem) {
        mGetItem = getItem;
    }


    @Override
    void onTakeView() {
        mGetItemSubscription = mGetItem.execute(new Subscriber<Item>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Item item) {
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
