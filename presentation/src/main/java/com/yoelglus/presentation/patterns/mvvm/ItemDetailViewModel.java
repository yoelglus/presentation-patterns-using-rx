package com.yoelglus.presentation.patterns.mvvm;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.model.ItemModel;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class ItemDetailViewModel extends AbstractViewModel {

    private ItemsRepository itemsRepository;
    private String itemId;
    private PublishSubject<ItemModel> itemModelSubject = PublishSubject.create();
    private Subscription getItemSubscription;

    public ItemDetailViewModel(ItemsRepository itemsRepository, String itemId) {
        this.itemsRepository = itemsRepository;
        this.itemId = itemId;
    }

    @Override
    public void onStart() {
        super.onStart();
        getItemSubscription = itemsRepository.getItem(itemId)
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
