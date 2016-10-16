package com.yoelglus.presentation.patterns.rmvp;

abstract class AbstractPresenter<T> {

    T view;

    public abstract void onTakeView();

    public abstract void onDropView();

    void takeView(T view) {
        this.view = view;
        onTakeView();
    }

    void dropView(T view) {
        if (this.view == view) {
            this.view = null;
            onDropView();
        }
    }
}
