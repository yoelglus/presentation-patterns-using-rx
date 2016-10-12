package com.yoelglus.presentation.patterns.presenter;

public abstract class AbstractPresenter<T> {

    protected T mView;

    public void takeView(T view) {
        mView = view;
        onTakeView();
    }

    public void dropView(T view) {
        if (mView == view) {
            mView = null;
            onDropView();
        }
    }

    public abstract void onTakeView();

    public abstract void onDropView();
}
