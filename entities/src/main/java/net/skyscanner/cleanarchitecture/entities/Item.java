package net.skyscanner.cleanarchitecture.entities;

public class Item {
    private String id;
    private String content;
    private String detail;


    public Item(String id, String content, String detail) {
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
}
