package com.yoelglus.presentation.patterns.mvc;

import com.yoelglus.presentation.patterns.entities.Item;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

import static rx.subjects.PublishSubject.create;

public class MvcItemsListModel {

    private PublishSubject<List<Item>> mItemsSubject = create();

    public void setItems(List<Item> items) {
        mItemsSubject.onNext(items);
    }

    Observable<List<Item>> itemsList() {
        return mItemsSubject;
    }
}
