package com.yoelglus.presentation.patterns.presentation.mapper;

import com.yoelglus.presentation.patterns.entities.Item;
import com.yoelglus.presentation.patterns.presentation.model.ItemModel;

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
