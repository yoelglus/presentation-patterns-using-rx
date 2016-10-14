package com.yoelglus.presentation.patterns.mvpvm;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemsListViewModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import rx.Subscription;

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
        mGetItemsSubscription = mGetItems.execute(null)
                .map(mItemModelsMapper::map)
                .map(ItemsListViewModel::new)
                .subscribe(this::notifyOnChange);
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
