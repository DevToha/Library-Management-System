package model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String memberId;
    private String itemId;

    public Transaction(String transactionId, String memberId, String itemId, Date issueDate) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.returnDate = null;
    }

    public String getTransactionId() { return transactionId; }
    public String getMemberId() { return memberId; }
    public String getItemId() { return itemId; }
}