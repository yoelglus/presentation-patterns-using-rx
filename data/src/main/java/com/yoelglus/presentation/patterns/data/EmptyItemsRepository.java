package com.yoelglus.presentation.patterns.data;

import com.yoelglus.presentation.patterns.domain.interfaces.ItemsRepository;
import com.yoelglus.presentation.patterns.entities.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

import static rx.Observable.just;


public class EmptyItemsRepository implements ItemsRepository {
    private static final List<Item> ITEMS = new ArrayList<>();
    private static final Map<String, Item> ITEM_MAP = new HashMap<>();

    @Override
    public Observable<List<Item>> getItems() {
        return just(ITEMS);
    }

    @Override
    public Observable<Item> getItem(String id) {
        return just(ITEM_MAP.get(id));
    }

    @Override
    public Observable<String> addItem(String content, String detail) {
        String id = String.valueOf(ITEMS.size() + 1);
        addItem(new Item(id, content, detail));
        return just(id);
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }
}
