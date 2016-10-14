package com.yoelglus.presentation.patterns.viewmodel;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class ItemDetailViewModel extends AbstractViewModel {

    private ItemsRepository mItemsRepository;
    private String mItemId;
    private PublishSubject<ItemModel> mItemModelSubject = PublishSubject.create();
    private Subscription mGetItemSubscription;
    private Scheduler mIoScheduler;
    private Scheduler mMainScheduler;

    public ItemDetailViewModel(ItemsRepository itemsRepository,
                               String itemId,
                               Scheduler ioScheduler,
                               Scheduler mainScheduler) {
        mItemsRepository = itemsRepository;
        mItemId = itemId;
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetItemSubscription = mItemsRepository.getItem(mItemId)
                .subscribeOn(mIoScheduler)
                .observeOn(mMainScheduler)
                .map(ItemModel::from)
                .subscribe(mItemModelSubject);
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
