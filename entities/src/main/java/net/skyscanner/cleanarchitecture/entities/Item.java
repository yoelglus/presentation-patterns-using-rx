package net.skyscanner.cleanarchitecture.entities;

public class Item {
// State
    private String id;
    private String content;
    private String detail;


    public Item(String id, String content, String detail) {
        this.id = id;
        this.content = content;
        this.detail = detail;
    }


// region Getters & Setters
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
