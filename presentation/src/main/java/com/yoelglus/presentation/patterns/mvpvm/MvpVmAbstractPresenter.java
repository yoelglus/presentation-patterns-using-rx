package com.yoelglus.presentation.patterns.mvpvm;

import rx.Observable;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

import static rx.subjects.PublishSubject.create;

abstract class MvpVmAbstractPresenter<T> {

    private PublishSubject<T> mViewModelSubject = create();

    Observable<T> getViewModelObservable() {
        return mViewModelSubject.doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                onUnSubscribe();
            }
        }).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                onSubscribe();
            }
        });
    }

    void notifyOnChange(T viewModel) {
        mViewModelSubject.onNext(viewModel);
    }

    protected void onSubscribe() {
    }

    protected void onUnSubscribe() {
    }
}
