package com.yoelglus.presentation.patterns.model;

import com.yoelglus.presentation.patterns.entities.Item;

public class ItemModel {
    private final String id;
    private final String content;
    private final String detail;

    private ItemModel(String id, String content, String detail) {
        this.id = id;
        this.content = content;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDetail() {
        return detail;
    }

    public static ItemModel from(Item item) {
        return new ItemModel(item.getId(), item.getContent(), item.getDetail());
    }
}
