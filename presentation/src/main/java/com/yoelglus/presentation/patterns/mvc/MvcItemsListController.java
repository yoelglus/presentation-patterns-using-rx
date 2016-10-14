package com.yoelglus.presentation.patterns.mvc;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import rx.functions.Action1;

public class MvcItemsListController {

    private MvcItemsListModel mMvcItemsListModel;
    private GetItems mGetItems;
    private Navigator mNavigator;

    public MvcItemsListController(GetItems getItems, Navigator navigator, MvcItemsListModel mvcItemsListModel) {
        mMvcItemsListModel = mvcItemsListModel;
        mGetItems = getItems;
        mNavigator = navigator;
    }

    public Action1<Void> addItemClicks() {
        return aVoid -> mNavigator.navigateToAddItem();
    }

    void loadItemsList() {
        mGetItems.execute(null).subscribe(mMvcItemsListModel::setItems);
    }

    Action1<String> itemClicked() {
        return id -> mNavigator.navigateToItem(id);
    }

}
