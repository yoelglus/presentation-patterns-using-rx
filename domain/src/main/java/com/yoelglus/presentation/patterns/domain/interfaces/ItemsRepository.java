package com.yoelglus.presentation.patterns.domain.interfaces;


import com.yoelglus.presentation.patterns.entities.Item;

import java.util.List;

import rx.Observable;

public interface ItemsRepository {
    Observable<List<Item>> getItems();

    Observable<Item> getItem(String id);

    Observable<String> addItem(String content, String detail);
}
