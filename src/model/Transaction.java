package model;

public class Transaction {

    private String transactionId;
    private String memberId;
    private String itemId;
    private String issueDate;
    private String returnDate;
    private String status;

    public Transaction(String transactionId, String memberId, String itemId,
                       String issueDate, String returnDate, String status) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.itemId = itemId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}