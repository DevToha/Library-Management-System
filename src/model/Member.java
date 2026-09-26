package model;

public class Member extends Person {
    private String memberType;
    private int maxAllowedItems;

    public Member(String id, String name, String email, String memberType) {
        super(id, name, email);
        this.memberType = memberType;
        this.maxAllowedItems = memberType.equalsIgnoreCase("Faculty") ? 10 : 5;
    }

    public String getMemberType() { return memberType; }
    public void setMemberType(String memberType) { this.memberType = memberType; }

    public int getMaxAllowedItems() { return maxAllowedItems; }
    public void setMaxAllowedItems(int maxAllowedItems) { this.maxAllowedItems = maxAllowedItems; }
}