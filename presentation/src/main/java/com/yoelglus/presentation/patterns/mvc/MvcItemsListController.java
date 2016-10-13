package com.yoelglus.presentation.patterns.mvc;

import com.yoelglus.presentation.patterns.domain.usecases.GetItems;
import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import java.util.List;

import rx.functions.Action1;

public class MvcItemsListController {

    private MvcItemsListModel mMvcItemsListModel;
    private GetItems mGetItems;
    private Navigator mNavigator;

    public MvcItemsListController(GetItems getItems,
                                  Navigator navigator,
                                  MvcItemsListModel mvcItemsListModel) {
        mMvcItemsListModel = mvcItemsListModel;
        mGetItems = getItems;
        mNavigator = navigator;
    }

    void loadItemsList() {
        mGetItems.execute().subscribe(new Action1<List<Item>>() {
            @Override
            public void call(List<Item> items) {
                mMvcItemsListModel.setItems(items);
            }
        });
    }

    Action1<String> itemClicked() {
        return new Action1<String>() {
            @Override
            public void call(String id) {
                mNavigator.navigateToItem(id);
            }
        };
    }

    public Action1<Void> addItemClicks() {
        return new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mNavigator.navigateToAddItem();
            }
        };
    }

}
