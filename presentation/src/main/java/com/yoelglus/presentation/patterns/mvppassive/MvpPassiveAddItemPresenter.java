package com.yoelglus.presentation.patterns.mvppassive;

import com.yoelglus.presentation.patterns.domain.usecases.AddItem;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import rx.Observable;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;

public class MvpPassiveAddItemPresenter extends AbstractPresenter<MvpPassiveAddItemPresenter.View> {

    private AddItem mAddItem;
    private String mContentText;
    private String mDetailText;
    private SubscriptionList mSubscriptionList = new SubscriptionList();

    public MvpPassiveAddItemPresenter(AddItem addItem) {
        mAddItem = addItem;
    }

    private void setAddButtonEnableState() {
        mView.setAddButtonEnabled(mContentText.length() > 0 && mDetailText.length() > 0);
    }

    @Override
    public void onTakeView() {
        mSubscriptionList.add(mView.contentTextChanged().subscribe(new Action1<String>() {
            @Override
            public void call(String contentText) {
                mContentText = contentText;
                setAddButtonEnableState();
            }
        }));
        mSubscriptionList.add(mView.detailTextChanged().subscribe(new Action1<String>() {
            @Override
            public void call(String detailText) {
                mDetailText = detailText;
                setAddButtonEnableState();
            }
        }));

        mSubscriptionList.add(mView.addButtonClicks().subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mAddItem.execute(mContentText, mDetailText).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mView.dismissView();
                    }
                });
            }
        }));

        mSubscriptionList.add(mView.cancelButtonClicks().subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mView.dismissView();
            }
        }));
    }

    @Override
    public void onDropView() {
        mSubscriptionList.unsubscribe();
    }

    public interface View {
        void setAddButtonEnabled(boolean enabled);

        Observable<String> contentTextChanged();

        Observable<String> detailTextChanged();

        Observable<Void> addButtonClicks();

        Observable<Void> cancelButtonClicks();

        void dismissView();
    }
}