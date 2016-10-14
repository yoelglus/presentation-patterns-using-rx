package com.yoelglus.presentation.patterns.domain.usecases;

import rx.Observable;
import rx.Scheduler;

abstract class AbstractUseCase<R, P> {

    P mParam;

    private Scheduler mIoScheduler;
    private Scheduler mMainScheduler;

    AbstractUseCase(Scheduler ioScheduler, Scheduler mainScheduler) {
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
    }

    public Observable<R> execute(P param) {
        mParam = param;
        return getObservable().subscribeOn(mIoScheduler).observeOn(mMainScheduler);
    }

    public abstract Observable<R> getObservable();
}
