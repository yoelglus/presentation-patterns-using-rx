package com.yoelglus.presentation.patterns.domain.usecases;

import rx.Observable;
import rx.Scheduler;

abstract class AbstractUseCase<T> {

    private final Scheduler mIoScheduler;
    private final Scheduler mMainScheduler;

    AbstractUseCase(Scheduler ioScheduler, Scheduler mainScheduler) {
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
    }

    public Observable<T> execute() {
        return getObservable().subscribeOn(mIoScheduler).observeOn(mMainScheduler);
    }

    protected abstract Observable<T> getObservable();
}
