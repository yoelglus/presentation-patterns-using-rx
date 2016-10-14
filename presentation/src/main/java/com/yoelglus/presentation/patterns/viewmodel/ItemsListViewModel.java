package com.yoelglus.presentation.patterns.viewmodel;

import com.yoelglus.presentation.patterns.data.ItemsRepository;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class ItemsListViewModel extends AbstractViewModel {

    private ItemsRepository mItemsRepository;
    private Navigator mNavigator;
    private PublishSubject<List<ItemModel>> mItemModelsSubject = PublishSubject.create();
    private Action1<Void> mAddItemClicks = aVoid -> mNavigator.navigateToAddItem();
    private Action1<String> mItemClicks = id -> mNavigator.navigateToItem(id);
    private Subscription mGetItemsSubscription;

    public ItemsListViewModel(ItemsRepository itemsRepository, Navigator navigator) {
        mItemsRepository = itemsRepository;
        mNavigator = navigator;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetItemsSubscription = mItemsRepository.getItems().map(ItemModelsMapper::map).subscribe(mItemModelsSubject);
    }

    @Override
    public void onStop() {
        super.onStop();
        mGetItemsSubscription.unsubscribe();
    }

    public Observable<List<ItemModel>> itemModels() {
        return mItemModelsSubject;
    }

    public Action1<Void> addItemClicks() {
        return mAddItemClicks;
    }

    public Action1<String> itemClicks() {
        return mItemClicks;
    }

}
