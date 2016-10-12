package com.yoelglus.presentation.patterns.domain.usecases;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

public abstract class AbstractUseCase<T> {

    protected Scheduler mIoScheduler;
    protected Scheduler mMainScheduler;

    public AbstractUseCase(Scheduler ioScheduler, Scheduler mainScheduler) {
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
    }

    public Observable<T> execute() {
        return getObservable().subscribeOn(mIoScheduler).observeOn(mMainScheduler);
    }

    public abstract Observable<T> getObservable();
}
