package com.yoelglus.presentation.patterns.viewmodel;

import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class ItemDetailViewModel extends AbstractViewModel {

    private GetItem mGetItem;
    private PublishSubject<ItemModel> mItemModelSubject = PublishSubject.create();
    private Subscription mGetItemSubscription;

    public ItemDetailViewModel(GetItem getItem) {
        mGetItem = getItem;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetItemSubscription = mGetItem.execute().subscribe(new Action1<Item>() {
            @Override
            public void call(Item item) {
                mItemModelSubject.onNext(ItemModel.from(item));
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mGetItemSubscription.unsubscribe();
    }

    public Observable<ItemModel> itemModel() {
        return mItemModelSubject;
    }
}
