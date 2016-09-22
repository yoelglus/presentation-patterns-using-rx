package net.skyscanner.cleanarchitecture.presentation.presenter;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public abstract class AbstractPresenter<T> {

    private PublishSubject<T> mViewModelSubject = PublishSubject.create();

    protected AbstractPresenter() {
        mViewModelSubject.doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                onUnSubscribe();
            }
        });
    }

    public Subscription subscribe(Action1<T> onLoadAction) {
        Subscription subscription = mViewModelSubject.subscribe(onLoadAction);
        onSubscribe();
        return subscription;
    }

    protected void notifyOnChange(T viewModel) {
        mViewModelSubject.onNext(viewModel);
    }


    protected void onSubscribe() {}

    protected void onUnSubscribe() {}
}
