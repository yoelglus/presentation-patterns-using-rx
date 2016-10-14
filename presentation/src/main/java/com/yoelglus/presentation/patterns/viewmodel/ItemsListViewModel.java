package com.yoelglus.presentation.patterns.viewmodel;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class ItemsListViewModel extends AbstractViewModel {

    private ItemsRepository mItemsRepository;
    private Navigator mNavigator;
    private PublishSubject<List<ItemModel>> mItemModelsSubject = PublishSubject.create();
    private Subscription mGetItemsSubscription;
    private Scheduler mIoScheduler;
    private Scheduler mMainScheduler;

    public ItemsListViewModel(ItemsRepository itemsRepository,
                              Navigator navigator,
                              Scheduler ioScheduler,
                              Scheduler mainScheduler) {
        mItemsRepository = itemsRepository;
        mNavigator = navigator;
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetItemsSubscription = mItemsRepository.getItems()
                .subscribeOn(mIoScheduler)
                .observeOn(mMainScheduler)
                .map(ItemModelsMapper::map)
                .subscribe(mItemModelsSubject);
    }

    @Override
    public void onStop() {
        super.onStop();
        mGetItemsSubscription.unsubscribe();
    }

    public Observable<List<ItemModel>> itemModels() {
        return mItemModelsSubject.asObservable();
    }

    public void addItemClicked() {
        mNavigator.navigateToAddItem();
    }

    public void itemClicked(String itemId) {
        mNavigator.navigateToItem(itemId);
    }

}
