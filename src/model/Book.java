package model;

public class Book extends Item {

    private String author;
    private String isbn;

    public Book(String id, String title, String category, String author, String isbn) {
        super(id, title, category);
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String getType() {
        return "Book";
    }

    @Override
    public double calculateLateFee(int daysLate) {
        return daysLate * 10.0;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}