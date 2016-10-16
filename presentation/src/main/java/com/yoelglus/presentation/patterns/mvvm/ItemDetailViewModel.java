package com.yoelglus.presentation.patterns.mvvm;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class ItemDetailViewModel extends AbstractViewModel {

    private ItemsRepository itemsRepository;
    private String itemId;
    private PublishSubject<ItemModel> itemModelSubject = PublishSubject.create();
    private Subscription getItemSubscription;
    private Scheduler ioScheduler;
    private Scheduler mainScheduler;

    public ItemDetailViewModel(ItemsRepository itemsRepository,
                               String itemId,
                               Scheduler ioScheduler,
                               Scheduler mainScheduler) {
        this.itemsRepository = itemsRepository;
        this.itemId = itemId;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void onStart() {
        super.onStart();
        getItemSubscription = itemsRepository.getItem(itemId)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .map(ItemModel::from)
                .subscribe(itemModelSubject);
    }

    @Override
    public void onStop() {
        super.onStop();
        getItemSubscription.unsubscribe();
    }

    Observable<ItemModel> itemModel() {
        return itemModelSubject.asObservable();
    }
}
