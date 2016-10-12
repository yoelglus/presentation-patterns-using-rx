package com.yoelglus.presentation.patterns.presentation.model;

import com.yoelglus.presentation.patterns.entities.Item;

public class ItemModel {
    private final String mId;
    private final String mContent;
    private final String mDetail;

    public ItemModel(String id, String content, String detail) {
        mId = id;
        mContent = content;
        mDetail = detail;
    }

    public String getId() {
        return mId;
    }

    public String getContent() {
        return mContent;
    }

    public String getDetail() {
        return mDetail;
    }

    public static ItemModel from(Item item) {
        return new ItemModel(item.getId(), item.getContent(), item.getDetail());
    }
}
