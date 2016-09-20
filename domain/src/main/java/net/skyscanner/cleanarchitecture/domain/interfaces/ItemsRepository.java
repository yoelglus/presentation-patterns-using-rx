package net.skyscanner.cleanarchitecture.domain.interfaces;


import net.skyscanner.cleanarchitecture.entities.Item;

import java.util.List;

import rx.Observable;

public interface ItemsRepository {
    Observable<List<Item>> getItems();

    Observable<Item> getItem(String id);
}
