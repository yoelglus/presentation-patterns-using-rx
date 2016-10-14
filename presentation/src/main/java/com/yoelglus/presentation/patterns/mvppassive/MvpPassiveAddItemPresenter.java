package com.yoelglus.presentation.patterns.mvppassive;

import com.yoelglus.presentation.patterns.domain.usecases.AddItem;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import rx.Observable;
import rx.internal.util.SubscriptionList;

public class MvpPassiveAddItemPresenter extends AbstractPresenter<MvpPassiveAddItemPresenter.View> {

    private AddItem mAddItem;
    private String mContentText;
    private String mDetailText;
    private SubscriptionList mSubscriptionList = new SubscriptionList();

    public MvpPassiveAddItemPresenter(AddItem addItem) {
        mAddItem = addItem;
    }

    @Override
    public void onTakeView() {
        mSubscriptionList.add(mView.contentTextChanged().subscribe(contentText -> {
            mContentText = contentText;
            setAddButtonEnableState();
        }));
        mSubscriptionList.add(mView.detailTextChanged().subscribe(detailText -> {
            mDetailText = detailText;
            setAddButtonEnableState();
        }));

        mSubscriptionList.add(mView.addButtonClicks().subscribe(aVoid -> {
            mAddItem.execute(new AddItem.AddItemParam(mContentText, mDetailText)).subscribe(s -> {
                mView.dismissView();
            });
        }));

        mSubscriptionList.add(mView.cancelButtonClicks().subscribe(aVoid -> {
            mView.dismissView();
        }));
    }

    @Override
    public void onDropView() {
        mSubscriptionList.unsubscribe();
    }

    private void setAddButtonEnableState() {
        mView.setAddButtonEnabled(mContentText.length() > 0 && mDetailText.length() > 0);
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
