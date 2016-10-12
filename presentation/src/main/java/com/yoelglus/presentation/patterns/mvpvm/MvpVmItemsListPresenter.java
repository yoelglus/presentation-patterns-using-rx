package com.yoelglus.presentation.patterns.mvpvm;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemsListViewModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

public class MvpVmItemsListPresenter extends MvpVmAbstractPresenter<ItemsListViewModel> {

    private GetItems mGetItems;
    private ItemModelsMapper mItemModelsMapper;
    private Navigator mNavigator;
    private Subscription mGetItemsSubscription;

    public MvpVmItemsListPresenter(GetItems getItems, ItemModelsMapper itemModelsMapper, Navigator navigator) {
        mGetItems = getItems;
        mItemModelsMapper = itemModelsMapper;
        mNavigator = navigator;
    }

    @Override
    protected void onSubscribe() {
        mGetItemsSubscription = mGetItems.execute().subscribe(new Action1<List<Item>>() {
            @Override
            public void call(List<Item> items) {
                notifyOnChange(new ItemsListViewModel(mItemModelsMapper.map(items)));
            }
        });
    }

    @Override
    protected void onUnSubscribe() {
        mGetItemsSubscription.unsubscribe();
    }

    void onItemClicked(String id) {
        mNavigator.navigateToItem(id);
    }

    void onAddItemClicked() {
        mNavigator.navigateToAddItem();
    }
}
