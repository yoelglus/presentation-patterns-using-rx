package com.yoelglus.presentation.patterns.mvppassiverx;


import com.yoelglus.presentation.patterns.domain.usecases.AddItem;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import rx.Observable;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;

public class MvpPassiveRxAddItemPresenter extends AbstractPresenter<MvpPassiveRxAddItemPresenter.View> {

    private AddItem mAddItem;
    private String mContentText;
    private String mDetailText;
    private SubscriptionList mSubscriptionList = new SubscriptionList();

    public MvpPassiveRxAddItemPresenter(AddItem addItem) {
        mAddItem = addItem;
    }

    @Override
    public void onTakeView() {
        mSubscriptionList.add(mView.contentTextChanged()
                .doOnNext(contentText -> mContentText = contentText)
                .zipWith(mView.detailTextChanged().doOnNext(detailText -> mDetailText = detailText),
                        (content, detail) -> content.length() > 0 && detail.length() > 0)
                .subscribe(mView.setAddButtonEnabled()));

        mSubscriptionList.add(mView.addButtonClicks()
                .flatMap(aVoid -> mAddItem.execute(new AddItem.AddItemParam(mContentText, mDetailText))
                        .map(s -> (Void) null))
                .subscribe(mView.dismissView()));

        mSubscriptionList.add(mView.cancelButtonClicks().subscribe(mView.dismissView()));
    }

    @Override
    public void onDropView() {
        mSubscriptionList.unsubscribe();
    }

    public interface View {
        Observable<String> contentTextChanged();

        Observable<String> detailTextChanged();

        Observable<Void> addButtonClicks();

        Observable<Void> cancelButtonClicks();

        Action1<Boolean> setAddButtonEnabled();

        Action1<Void> dismissView();
    }
}
