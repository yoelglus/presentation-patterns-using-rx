package com.yoelglus.presentation.patterns.mvc;

import com.yoelglus.presentation.patterns.domain.usecases.GetItem;
import com.yoelglus.presentation.patterns.entities.Item;

import rx.functions.Action1;

public class MvcItemDetailsController {

    private GetItem mGetItem;
    private MvcItemDetailsModel mMvcItemDetailsModel;

    public MvcItemDetailsController(GetItem getItem, MvcItemDetailsModel mvcItemDetailsModel) {
        mGetItem = getItem;
        mMvcItemDetailsModel = mvcItemDetailsModel;
    }

    void loadItemDetails() {
        mGetItem.execute().subscribe(new Action1<Item>() {
            @Override
            public void call(Item item) {
                mMvcItemDetailsModel.setItem(item);
            }
        });
    }
}
