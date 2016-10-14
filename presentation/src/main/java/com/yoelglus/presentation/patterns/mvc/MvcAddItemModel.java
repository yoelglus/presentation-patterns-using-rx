package com.yoelglus.presentation.patterns.mvc;

import rx.Observable;
import rx.subjects.PublishSubject;

import static rx.subjects.PublishSubject.create;

public class MvcAddItemModel {

    private PublishSubject<Boolean> mAddItemEnabledSubject = create();

    void setAddItemEnabled(boolean enabled) {
        mAddItemEnabledSubject.onNext(enabled);
    }

    Observable<Boolean> addItemEnabled() {
        return mAddItemEnabledSubject.asObservable();
    }

}
