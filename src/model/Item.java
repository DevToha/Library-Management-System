package model;

public abstract class Item {

    private String id;
    private String title;
    private String category;
    private boolean available;

    public Item(String id, String title, String category) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.available = true;
    }

    public abstract String getType();

    public abstract double calculateLateFee(int daysLate);

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}