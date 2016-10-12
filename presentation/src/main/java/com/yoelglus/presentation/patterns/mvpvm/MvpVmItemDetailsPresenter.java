package com.yoelglus.presentation.patterns.mvpvm;

import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.model.ItemDetailViewModel;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Subscription;
import rx.functions.Action1;

public class MvpVmItemDetailsPresenter extends MvpVmAbstractPresenter<ItemDetailViewModel> {

    private GetItem mGetItem;
    private Subscription mGetItemSubscription;

    public MvpVmItemDetailsPresenter(GetItem getItem) {
        mGetItem = getItem;
    }

    @Override
    protected void onSubscribe() {
        super.onSubscribe();
        mGetItemSubscription = mGetItem.execute().subscribe(new Action1<Item>() {
            @Override
            public void call(Item item) {
                notifyOnChange(new ItemDetailViewModel(ItemModel.from(item)));
            }
        });
    }

    @Override
    protected void onUnSubscribe() {
        super.onUnSubscribe();
        mGetItemSubscription.unsubscribe();
    }

    public interface View {
    }
}
