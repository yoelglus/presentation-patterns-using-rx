package com.yoelglus.presentation.patterns.mvvm;

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

    private ItemsRepository itemsRepository;
    private Navigator navigator;
    private PublishSubject<List<ItemModel>> itemModelsSubject = PublishSubject.create();
    private Subscription getItemsSubscription;
    private Scheduler ioScheduler;
    private Scheduler mainScheduler;

    public ItemsListViewModel(ItemsRepository itemsRepository,
                              Navigator navigator,
                              Scheduler ioScheduler,
                              Scheduler mainScheduler) {
        this.itemsRepository = itemsRepository;
        this.navigator = navigator;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void onStart() {
        super.onStart();
        getItemsSubscription = itemsRepository.getItems()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .map(ItemModelsMapper::map)
                .subscribe(itemModelsSubject);
    }

    @Override
    public void onStop() {
        super.onStop();
        getItemsSubscription.unsubscribe();
    }

    Observable<List<ItemModel>> itemModels() {
        return itemModelsSubject.asObservable();
    }

    void addItemClicked() {
        navigator.navigateToAddItem();
    }

    void itemClicked(String itemId) {
        navigator.navigateToItem(itemId);
    }

}