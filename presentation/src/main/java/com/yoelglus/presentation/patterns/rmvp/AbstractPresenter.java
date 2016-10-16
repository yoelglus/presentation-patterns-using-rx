package com.yoelglus.presentation.patterns.rmvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

abstract class AbstractPresenter<T> {

    T view;

    private CompositeSubscription compositeSubscription;

    protected abstract void onTakeView();

    protected void onDropView() {
    }

    public void takeView(T view) {
        compositeSubscription = new CompositeSubscription();
        this.view = view;
        onTakeView();
    }

    public void dropView(T view) {
        compositeSubscription.unsubscribe();
        compositeSubscription = null;
        if (this.view == view) {
            this.view = null;
            onDropView();
        }
    }

    protected void unsubscribeOnViewDropped(Subscription subscription) {
        compositeSubscription.add(subscription);
    }
}
