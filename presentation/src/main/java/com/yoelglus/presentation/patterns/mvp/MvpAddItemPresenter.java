package com.yoelglus.presentation.patterns.mvp;

import com.yoelglus.presentation.patterns.domain.usecases.AddItem;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

public class MvpAddItemPresenter extends AbstractPresenter<MvpAddItemPresenter.View> {

    private AddItem mAddItem;
    private String mContentText = "";
    private String mDetailText = "";

    public MvpAddItemPresenter(AddItem addItem) {
        mAddItem = addItem;
    }

    void onAddButtonClicked() {
        mAddItem.execute(mContentText, mDetailText).subscribe(s -> {
            mView.dismissView();
        });
    }

    void onDismissButtonClicked() {
        mView.dismissView();
    }

    void onContentTextChanged(String contentText) {
        mContentText = contentText;
        setAddButtonEnableState();
    }

    void onDetailTextChanged(String detailText) {
        mDetailText = detailText;
        setAddButtonEnableState();
    }

    private void setAddButtonEnableState() {
        mView.setAddButtonEnabled(mContentText.length() > 0 && mDetailText.length() > 0);
    }

    @Override
    public void onTakeView() {
        setAddButtonEnableState();
    }

    @Override
    public void onDropView() {
    }

    public interface View {
        void setAddButtonEnabled(boolean enabled);
        void dismissView();
    }
}
