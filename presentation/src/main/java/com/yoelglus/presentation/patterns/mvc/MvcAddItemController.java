package com.yoelglus.presentation.patterns.mvc;

import com.yoelglus.presentation.patterns.domain.usecases.AddItem;
import com.yoelglus.presentation.patterns.navigator.Navigator;

public class MvcAddItemController {

    private MvcAddItemModel mMvcAddItemModel;
    private AddItem mAddItem;
    private CharSequence mContent = "";
    private CharSequence mDetail = "";
    private Navigator mNavigator;

    public MvcAddItemController(AddItem addItem, Navigator navigator, MvcAddItemModel mvcAddItemModel) {
        mAddItem = addItem;
        mNavigator = navigator;
        mMvcAddItemModel = mvcAddItemModel;
    }

    private void updateModel() {
        mMvcAddItemModel.setAddItemEnabled(mContent.length() > 0 && mDetail.length() > 0);
    }

    void contentTextChanged(CharSequence content) {
        mContent = content;
        updateModel();
    }

    void detailTextChanged(CharSequence detail) {
        mDetail = detail;
        updateModel();
    }

    void addButtonClicked() {
        mAddItem.execute(mContent.toString(), mDetail.toString()).subscribe(s -> {
            mNavigator.closeCurrentScreen();
        });
    }

    void dismissButtonClicked() {
        mNavigator.closeCurrentScreen();
    }
}
