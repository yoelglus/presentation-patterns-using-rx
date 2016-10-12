package com.yoelglus.presentation.patterns.domain.usecases;

import rx.Observable;
import rx.Scheduler;

public abstract class AbstractUseCase<T> {

    private Scheduler mIoScheduler;
    private Scheduler mMainScheduler;

    AbstractUseCase(Scheduler ioScheduler, Scheduler mainScheduler) {
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
    }

    public Observable<T> execute() {
        return getObservable().subscribeOn(mIoScheduler).observeOn(mMainScheduler);
    }

    public abstract Observable<T> getObservable();
}
