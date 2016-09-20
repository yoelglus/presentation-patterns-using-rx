package net.skyscanner.cleanarchitecture.presentation.presenter;

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

    abstract void onTakeView();

    abstract void onDropView();
}
