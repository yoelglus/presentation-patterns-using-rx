package com.yoelglus.presentation.patterns.mvpvm;


import com.yoelglus.presentation.patterns.domain.usecases.AddItem;
import com.yoelglus.presentation.patterns.model.AddItemViewModel;

public class MvpVmAddItemPresenter extends MvpVmAbstractPresenter<AddItemViewModel> {

    private AddItem mAddItem;
    private String mContentText = "";
    private String mDetailText = "";

    public MvpVmAddItemPresenter(AddItem addItem) {
        mAddItem = addItem;
    }

    private void setAddButtonEnableState() {
        notifyOnChange(new AddItemViewModel(mContentText.length() > 0 && mDetailText.length() > 0, false));
    }

    void onContentTextChanged(String contentText) {
        mContentText = contentText;
        setAddButtonEnableState();
    }

    void onDetailTextChanged(String detailText) {
        mDetailText = detailText;
        setAddButtonEnableState();
    }

    void onAddButtonClicked() {
        mAddItem.execute(new AddItem.AddItemParam(mContentText, mDetailText))
                .map(s -> new AddItemViewModel(true, true))
                .subscribe(this::notifyOnChange);
    }

    void onCancelButtonClicked() {
        notifyOnChange(new AddItemViewModel(true, true));
    }

    @Override
    protected void onSubscribe() {
        super.onSubscribe();
        setAddButtonEnableState();
    }
}
