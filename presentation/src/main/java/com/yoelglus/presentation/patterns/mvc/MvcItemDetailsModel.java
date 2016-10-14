package com.yoelglus.presentation.patterns.mvc;

import com.yoelglus.presentation.patterns.entities.Item;

import rx.Observable;
import rx.subjects.PublishSubject;

import static rx.subjects.PublishSubject.create;

public class MvcItemDetailsModel {

    private PublishSubject<Item> mItemSubject = create();

    public void setItem(Item item) {
        mItemSubject.onNext(item);
    }

    Observable<Item> itemDetails() {
        return mItemSubject.asObservable();
    }

}
