package com.yoelglus.presentation.patterns.mvpvm;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.mapper.ItemModelsMapper;
import com.yoelglus.presentation.patterns.model.ItemsListViewModel;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import rx.Subscription;

public class MvpVmItemsListPresenter extends MvpVmAbstractPresenter<ItemsListViewModel> {

    private GetItems mGetItems;
    private Navigator mNavigator;
    private Subscription mGetItemsSubscription;

    public MvpVmItemsListPresenter(GetItems getItems, Navigator navigator) {
        mGetItems = getItems;
        mNavigator = navigator;
    }

    @Override
    protected void onSubscribe() {
        mGetItemsSubscription = mGetItems.execute().subscribe(items -> {
            notifyOnChange(new ItemsListViewModel(ItemModelsMapper.map(items)));
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
