package net.skyscanner.cleanarchitecture.presentation.model;

public class ItemDetailViewModel {

    private ItemModel mItemModel;

    public ItemDetailViewModel(ItemModel itemModel) {
        mItemModel = itemModel;
    }

    public ItemModel getItemModel() {
        return mItemModel;
    }
}
