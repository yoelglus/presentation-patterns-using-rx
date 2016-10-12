package com.yoelglus.presentation.patterns.model;

public class ItemDetailViewModel {

    private ItemModel mItemModel;

    public ItemDetailViewModel(ItemModel itemModel) {
        mItemModel = itemModel;
    }

    public ItemModel getItemModel() {
        return mItemModel;
    }
}
