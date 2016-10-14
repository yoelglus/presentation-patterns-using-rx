package com.yoelglus.presentation.patterns.viewmodel;

import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
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
    public void onStop() {
        super.onStop();
        mGetItemSubscription.unsubscribe();
    }

    public Action1<String> loadItem() {
        return itemId -> mGetItemSubscription = mGetItem.execute(itemId)
                .map(ItemModel::from)
                .subscribe(mItemModelSubject);
    }

    public Observable<ItemModel> itemModel() {
        return mItemModelSubject;
    }
}
