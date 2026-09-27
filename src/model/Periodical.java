package model;

public class Periodical extends Item {

    private String publisher;
    private int issueNumber;

    public Periodical(String id, String title, String category, String publisher, int issueNumber) {
        super(id, title, category);
        this.publisher = publisher;
        this.issueNumber = issueNumber;
    }

    @Override
    public String getType() {
        return "Periodical";
    }

    @Override
    public double calculateLateFee(int daysLate) {
        return daysLate * 5.0;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }
}