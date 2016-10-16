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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemModel itemModel = (ItemModel) o;

        if (id != null ? !id.equals(itemModel.id) : itemModel.id != null) {
            return false;
        }
        if (content != null ? !content.equals(itemModel.content) : itemModel.content != null) {
            return false;
        }
        return detail != null ? detail.equals(itemModel.detail) : itemModel.detail == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        return result;
    }
}
