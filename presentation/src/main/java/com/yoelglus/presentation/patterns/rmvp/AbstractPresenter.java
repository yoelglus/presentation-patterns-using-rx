package com.yoelglus.presentation.patterns.rmvp;

abstract class AbstractPresenter<T> {

    T mView;

    public abstract void onTakeView();

    public abstract void onDropView();

    void takeView(T view) {
        mView = view;
        onTakeView();
    }

    void dropView(T view) {
        if (mView == view) {
            mView = null;
            onDropView();
        }
    }
}
