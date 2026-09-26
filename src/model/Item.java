package model;

import java.io.Serializable;

public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private boolean isAvailable;

    public Item(String id, String title) {
        this.id = id;
        this.title = title;
        this.isAvailable = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public abstract String getItemType();
}