package com.yoelglus.presentation.patterns.viewmodel;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class ItemDetailViewModel extends AbstractViewModel {

    private ItemsRepository mItemsRepository;
    private String mItemId;
    private PublishSubject<ItemModel> mItemModelSubject = PublishSubject.create();
    private Subscription mGetItemSubscription;

    public ItemDetailViewModel(ItemsRepository itemsRepository, String itemId) {
        mItemsRepository = itemsRepository;
        mItemId = itemId;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetItemSubscription = mItemsRepository.getItem(mItemId).subscribe(item -> {
            mItemModelSubject.onNext(ItemModel.from(item));
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
