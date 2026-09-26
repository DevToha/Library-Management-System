package model;

public class Periodical extends Item {
    private String issueNumber;
    private String category;

    public Periodical(String id, String title, String issueNumber, String category) {
        super(id, title);
        this.issueNumber = issueNumber;
        this.category = category;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getItemType() {
        return "Periodical";
    }
}