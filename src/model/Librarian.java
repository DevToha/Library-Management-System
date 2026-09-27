package model;

public class Librarian extends Person {
    private String employeeId;
    private String department;

    public Librarian(String id, String name, String email, String employeeId, String department) {
        super(id, name, email);
        this.employeeId = employeeId;
        this.department = department;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}