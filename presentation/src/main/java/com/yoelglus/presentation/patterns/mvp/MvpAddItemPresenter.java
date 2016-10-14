package com.yoelglus.presentation.patterns.mvp;

import com.yoelglus.presentation.patterns.domain.usecases.AddItem;
import com.yoelglus.presentation.patterns.presenter.AbstractPresenter;

import rx.functions.Action1;

public class MvpAddItemPresenter extends AbstractPresenter<MvpAddItemPresenter.View> {

    private AddItem mAddItem;
    private CharSequence mContentText = "";
    private CharSequence mDetailText = "";

    public MvpAddItemPresenter(AddItem addItem) {
        mAddItem = addItem;
    }

    @Override
    public void onTakeView() {
        setAddButtonEnableState();
    }

    @Override
    public void onDropView() {
    }

    private void setAddButtonEnableState() {
        mView.addButtonEnabled().call(mContentText.length() > 0 && mDetailText.length() > 0);
    }

    Action1<Void> onAddButtonClicked() {
        return aVoid -> mAddItem.execute(new AddItem.AddItemParam(mContentText.toString(), mDetailText.toString()))
                .map(s -> (Void) null)
                .subscribe(mView.dismissView());
    }

    Action1<Void> onDismissButtonClicked() {
        return aVoid -> mView.dismissView();
    }

    Action1<CharSequence> onContentTextChanged() {
        return contentText -> {
            mContentText = contentText;
            setAddButtonEnableState();
        };
    }

    Action1<CharSequence> onDetailTextChanged() {
        return detailText -> {
            mDetailText = detailText;
            setAddButtonEnableState();
        };
    }

    public interface View {
        Action1<Boolean> addButtonEnabled();

        Action1<Void> dismissView();
    }
}
