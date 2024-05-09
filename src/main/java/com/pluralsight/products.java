package com.pluralsight;

public class products {
    private String id;
    private String name;
    private double price;
    private String department;

    public products(String id, String name, double price, String department) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price + ", Department: " + department;
    }
}


