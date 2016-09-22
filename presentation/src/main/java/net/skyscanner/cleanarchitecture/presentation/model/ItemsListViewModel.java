package net.skyscanner.cleanarchitecture.presentation.model;

import java.util.List;

public class ItemsListViewModel {
    private List<ItemModel> mItemModels;

    public ItemsListViewModel(List<ItemModel> itemModels) {
        mItemModels = itemModels;
    }

    public List<ItemModel> getItemModels() {
        return mItemModels;
    }
}
