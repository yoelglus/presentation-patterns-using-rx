package com.yoelglus.presentation.patterns.mapper;

import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ItemModelsMapper {
    public static List<ItemModel> map(List<Item> items) {
        List<ItemModel> itemModels = new ArrayList<>(items.size());
        for (Item item : items) {
            itemModels.add(ItemModel.from(item));
        }
        return itemModels;
    }
}
