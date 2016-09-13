package net.skyscanner.cleanarchitecture.presentation.mapper;

import net.skyscanner.cleanarchitecture.entities.Item;
import net.skyscanner.cleanarchitecture.presentation.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ItemModelsMapper {
    public List<ItemModel> map(List<Item> items) {
        List<ItemModel> itemModels = new ArrayList<>(items.size());
        for (Item item : items) {
            itemModels.add(ItemModel.from(item));
        }
        return itemModels;
    }
}
