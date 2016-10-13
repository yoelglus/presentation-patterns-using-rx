package com.yoelglus.presentation.patterns.mvc;

import com.yoelglus.presentation.patterns.domain.usecases.AddItem;
import com.yoelglus.presentation.patterns.navigator.Navigator;

import rx.functions.Action1;

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

    Action1<CharSequence> contentTextChanged() {
        return new Action1<CharSequence>() {
            @Override
            public void call(CharSequence content) {
                mContent = content;
                updateModel();
            }
        };
    }

    Action1<CharSequence> detailTextChanged() {
        return new Action1<CharSequence>() {
            @Override
            public void call(CharSequence detail) {
                mDetail = detail;
                updateModel();
            }
        };
    }

    Action1<Void> addButtonClicked() {
        return new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mAddItem.execute(mContent.toString(), mDetail.toString()).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mNavigator.closeCurrentScreen();
                    }
                });
            }
        };
    }

    Action1<Void> dismissButtonClicked() {
        return new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mNavigator.closeCurrentScreen();
            }
        };

    }

    private void updateModel() {
        mMvcAddItemModel.setAddItemEnabled(mContent.length() > 0 && mDetail.length() > 0);
    }
}
