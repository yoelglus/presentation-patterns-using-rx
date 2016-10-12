package com.yoelglus.presentation.patterns.viewmodel;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class ItemsListViewModel extends AbstractViewModel {

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Navigator mNavigator;
    private PublishSubject<List<ItemModel>> mItemModelsSubject = PublishSubject.create();
    private Action1<Void> mAddItemClicks = new Action1<Void>() {
        @Override
        public void call(Void aVoid) {
            mNavigator.navigateToAddItem();
        }
    };
    private Action1<String> mItemClicks = new Action1<String>() {
        @Override
        public void call(String id) {
            mNavigator.navigateToItem(id);
        }
    };
    private Subscription mGetItemsSubscription;

    public ItemsListViewModel(GetItems getItems, ItemModelsMapper itemModelsMapper, Navigator navigator) {
        mGetItems = getItems;
        mItemModelsMapper = itemModelsMapper;
        mNavigator = navigator;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetItemsSubscription = mGetItems.execute().subscribe(new Action1<List<Item>>() {
            @Override
            public void call(List<Item> items) {
                mItemModelsSubject.onNext(mItemModelsMapper.map(items));
            }
        });
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
