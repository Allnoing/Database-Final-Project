package com.example.databases;

/*
AUTHOR       : Sa'Cairo Bonner
CREATE DATE  : 4/08/2025
Updated      : 4/27/2025
Class        : ITN261
Class Section: 201
Assignment   : Final Project
PURPOSE      : take the inventory program we have been developing and turn it into a fully functional, database driven program.
Notes        :
 */


// === Fields representing each column from the 'products' table ===
public class Product {
    private int product_id;
    private String brand;
    private double price;
    private String description;
    private double weight;
    private double rating;

    // Constructor used to create a Product object with all the necessary fields available
    public Product(int product_id, String brand, double price, String description, double weight, double rating) {
        this.product_id = product_id;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.weight = weight;
        this.rating = rating;
    }

    // === Getter methods used to retrieve field values for display ===
    public int getId() {

        return product_id;
    }

    public String getBrand() {

        return brand;
    }

    public double getPrice() {

        return price;
    }

    public String getDescription() {

        return description;
    }


    public double getWeight() {

        return weight;
    }

    public double getRating() {

        return rating;
    }

    // Overriding toString to show a summary in the ListView
    @Override
    public String toString() {
        return description + " (" + brand + ")";
    }
}
