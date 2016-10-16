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

        if (mId != null ? !mId.equals(itemModel.mId) : itemModel.mId != null) {
            return false;
        }
        if (mContent != null ? !mContent.equals(itemModel.mContent) : itemModel.mContent != null) {
            return false;
        }
        return mDetail != null ? mDetail.equals(itemModel.mDetail) : itemModel.mDetail == null;

    }

    @Override
    public int hashCode() {
        int result = mId != null ? mId.hashCode() : 0;
        result = 31 * result + (mContent != null ? mContent.hashCode() : 0);
        result = 31 * result + (mDetail != null ? mDetail.hashCode() : 0);
        return result;
    }
}
