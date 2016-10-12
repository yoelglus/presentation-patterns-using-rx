package com.yoelglus.presentation.patterns.data;

import com.yoelglus.presentation.patterns.entities.Item;

import com.yoelglus.presentation.patterns.domain.interfaces.ItemsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

import static rx.Observable.just;

public class DummyItemsRepository implements ItemsRepository {

    private static final List<Item> ITEMS = new ArrayList<>();
    private static final Map<String, Item> ITEM_MAP = new HashMap<>();
    private static final int COUNT = 25;

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

    private static Item createDummyItem(int position) {
        return new Item(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }
}
