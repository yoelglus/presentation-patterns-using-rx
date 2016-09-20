package net.skyscanner.cleanarchitecture.data;

import net.skyscanner.cleanarchitecture.entities.Item;

import net.skyscanner.cleanarchitecture.domain.interfaces.ItemsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

public class DummyItemsRepository implements ItemsRepository {

    // Constants
    private static final List<Item> ITEMS = new ArrayList<>();
    private static final Map<String, Item> ITEM_MAP = new HashMap<>();
    private static final int COUNT = 25;

    // region Getters & Setters
    @Override
    public Observable<List<Item>> getItems() {
        return Observable.just(ITEMS);
    }

    // region Private methods
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
// endregion Private methods

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }
}
