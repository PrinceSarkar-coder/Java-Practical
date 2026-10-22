// Interface 1
interface Payable {
    void pay();
}

// Interface 2
interface Printable {
    void print();
}

// Abstract class
abstract class Employee {
    protected String name;

    Employee(String name) {
        this.name = name;
    }

    // abstract method: no body, subclasses must implement
    abstract double calculateSalary();

    // concrete method: has a body, reused by subclasses
    void showName() {
        System.out.println("Employee: " + name);
    }
}

// Subclass 1: fixed monthly salary
class FullTimeEmployee extends Employee implements Payable, Printable {
    private double monthlySalary;

    FullTimeEmployee(String name, double monthlySalary) {
        super(name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    double calculateSalary() {
        return monthlySalary;
    }

    @Override
    public void pay() {
        System.out.println("Paying " + calculateSalary() + " to " + name);
    }

    @Override
    public void print() {
        System.out.println("Full-time | " + name + " | Salary: " + calculateSalary());
    }
}

// Subclass 2: hourly salary
class PartTimeEmployee extends Employee implements Payable, Printable {
    private int hours;
    private double rate;

    PartTimeEmployee(String name, int hours, double rate) {
        super(name);
        this.hours = hours;
        this.rate = rate;
    }

    @Override
    double calculateSalary() {
        return hours * rate;
    }

    @Override
    public void pay() {
        System.out.println("Paying " + calculateSalary() + " to " + name);
    }

    @Override
    public void print() {
        System.out.println("Part-time | " + name + " | Salary: " + calculateSalary());
    }
}

public class Employeee {
    public static void main(String[] args) {
        Employee e1 = new FullTimeEmployee("Aman", 30000);
        Employee e2 = new PartTimeEmployee("Riya", 80, 250);

        e1.showName();                       // concrete method
        System.out.println(e1.calculateSalary());
        e2.showName();
        System.out.println(e2.calculateSalary());

        System.out.println("---");

        // Using interface references
        Payable p = new FullTimeEmployee("Aman", 30000);
        p.pay();

        Printable pr = new PartTimeEmployee("Riya", 80, 250);
        pr.print();

        // Employee e = new Employee("X");   // ERROR: cannot create object of abstract class
    }
}
