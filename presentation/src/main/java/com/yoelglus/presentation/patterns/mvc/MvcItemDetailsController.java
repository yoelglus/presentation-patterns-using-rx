package com.yoelglus.presentation.patterns.mvc;

import com.yoelglus.presentation.patterns.domain.usecases.GetItem;

public class MvcItemDetailsController {

    private GetItem mGetItem;
    private MvcItemDetailsModel mMvcItemDetailsModel;

    public MvcItemDetailsController(GetItem getItem, MvcItemDetailsModel mvcItemDetailsModel) {
        mGetItem = getItem;
        mMvcItemDetailsModel = mvcItemDetailsModel;
    }

    void loadItemDetails(String id) {
        mGetItem.execute(id).subscribe(mMvcItemDetailsModel::setItem);
    }
}
