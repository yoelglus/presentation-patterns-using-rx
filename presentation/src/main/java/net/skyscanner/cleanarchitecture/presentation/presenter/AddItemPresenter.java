package net.skyscanner.cleanarchitecture.presentation.presenter;

import net.skyscanner.cleanarchitecture.domain.usecases.AddItem;

import rx.Subscriber;
import rx.internal.util.SubscriptionList;

public class AddItemPresenter extends AbstractPresenter<AddItemPresenter.View> {

    private AddItem mAddItem;
    private String mContentText = "";
    private String mDetailText = "";

    public AddItemPresenter(AddItem addItem) {
        mAddItem = addItem;
    }

    public void onAddButtonClicked() {
        mAddItem.execute(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String id) {
                mView.dismissView();
            }
        }, mContentText, mDetailText);
    }

    public void onDismissButtonClicked() {
        mView.dismissView();
    }

    public void onContentTextChanged(String contentText) {
        mContentText = contentText;
        setAddButtonEnableState();
    }

    public void onDetailTextChanged(String detailText) {
        mDetailText = detailText;
        setAddButtonEnableState();
    }

    private void setAddButtonEnableState() {
        mView.setAddButtonEnabled(mContentText.length() > 0 && mDetailText.length() > 0);
    }

    @Override
    void onTakeView() {
        setAddButtonEnableState();
    }

    @Override
    void onDropView() {
    }

    public interface View {
        void setAddButtonEnabled(boolean enabled);
        void dismissView();
    }
}
