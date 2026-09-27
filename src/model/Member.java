package model;

public class Member extends Person {

    private int borrowCount;

    public Member(String id, String name, String email, String phone) {
        super(id, name, email, phone);
        this.borrowCount = 0;
    }

    @Override
    public String getRole() {
        return "Member";
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }
}