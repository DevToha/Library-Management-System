package model;

public class Librarian extends Person {

    private double salary;

    public Librarian(String id, String name, String email, String phone, double salary) {
        super(id, name, email, phone);
        this.salary = salary;
    }

    @Override
    public String getRole() {
        return "Librarian";
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}