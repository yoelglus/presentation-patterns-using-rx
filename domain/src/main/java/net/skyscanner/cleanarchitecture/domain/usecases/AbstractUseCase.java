package net.skyscanner.cleanarchitecture.domain.usecases;

import rx.Observable;
import rx.Scheduler;

public abstract class AbstractUseCase<T> {

    protected Scheduler mIoScheduler;
    protected Scheduler mMainScheduler;

    public AbstractUseCase(Scheduler ioScheduler, Scheduler mainScheduler) {
        mIoScheduler = ioScheduler;
        mMainScheduler = mainScheduler;
    }

    public abstract Observable<T> getObservable();

    public Observable<T> execute() {
        return getObservable().subscribeOn(mIoScheduler).observeOn(mMainScheduler);
    }
}
