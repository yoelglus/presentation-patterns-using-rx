package com.yoelglus.presentation.patterns.model;

import com.yoelglus.presentation.patterns.entities.Item;

public class ItemModel {
    private final String mId;
    private final String mContent;
    private final String mDetail;

    private ItemModel(String id, String content, String detail) {
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
