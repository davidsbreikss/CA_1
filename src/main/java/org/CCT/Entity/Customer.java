package org.CCT.Entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
// Lombok annotations to automatically generate getter methods for all fields
// and a toString method for this class.
public class Customer {

    // Fields for customers information
    private final String fullName;
    private final double totalPurchase;
    private final int customerClass;
    private final int lastPurchase;
    private final double discountedValue;

    // Constructor that initializes the Customer without a discounted value, defaulting to 0.0.
    public Customer(String fullName, double totalPurchase, int customerClass, int lastPurchase) {
        // Calls the overloaded constructor with discountedValue set to 0.0
        this(fullName, totalPurchase, customerClass, lastPurchase, 0.0);
    }

    // Overloaded constructor that allows setting the discounted value.
    public Customer(String fullName, double totalPurchase, int customerClass, int lastPurchase, double discountedValue) {
        // Assigns the parameter values to the corresponding fields.
        this.fullName = fullName;
        this.totalPurchase = totalPurchase;
        this.customerClass = customerClass;
        this.lastPurchase = lastPurchase;
        this.discountedValue = discountedValue;
    }
}