package net.skyscanner.cleanarchitecture.presentation.presenter;

import rx.Observable;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;

public class AddItemPresenter extends AbstractPresenter<AddItemPresenter.View> {

    private String mContentText;
    private String mDetailText;
    private SubscriptionList mSubscriptionList = new SubscriptionList();

    @Override
    void onTakeView() {
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

            }
        }));

        mSubscriptionList.add(mView.cancelButtonClicks().subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

            }
        }));
    }

    private void setAddButtonEnableState() {
        mView.setAddButtonEnabled(mContentText.length() > 0 && mDetailText.length() > 0);
    }

    @Override
    void onDropView() {
        mSubscriptionList.unsubscribe();
    }

    public interface View {
        Observable<String> contentTextChanged();

        Observable<String> detailTextChanged();

        Observable<Void> addButtonClicks();

        Observable<Void> cancelButtonClicks();

        void setAddButtonEnabled(boolean enabled);
    }
}
