package net.skyscanner.cleanarchitecture.domain.usecases;

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

    public Subscription execute(Subscriber<T> subscriber) {
        return getObservable().subscribeOn(mIoScheduler).observeOn(mMainScheduler).subscribe(subscriber);
    }

    public abstract Observable<T> getObservable();
}
