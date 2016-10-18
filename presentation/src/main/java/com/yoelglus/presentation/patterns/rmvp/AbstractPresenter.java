package com.yoelglus.presentation.patterns.rmvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

abstract class AbstractPresenter<T> {

    private CompositeSubscription compositeSubscription;

    protected abstract void onTakeView(T view);

    protected void onDropView(T view) {
    }

    void takeView(T view) {
        compositeSubscription = new CompositeSubscription();
        onTakeView(view);
    }

    void dropView(T view) {
        compositeSubscription.unsubscribe();
        compositeSubscription = null;
        onDropView(view);
    }

    void unsubscribeOnViewDropped(Subscription subscription) {
        compositeSubscription.add(subscription);
    }
}
