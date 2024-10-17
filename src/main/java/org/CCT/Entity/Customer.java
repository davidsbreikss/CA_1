package org.CCT.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
// Lombok annotations to automatically generate getter methods for all fields
// and a toString method for this class.
public class Customer {

    // Fields for customer information
    @JsonProperty("fullName") // Annotates the fullName field for JSON serialization
    private final String fullName; // Customer's full name
    @JsonIgnore // Ignores this field during JSON serialization
    private final double totalPurchase; // Total purchase amount by the customer
    @JsonIgnore // Ignores this field during JSON serialization
    private final int customerClass; // Class of the customer
    @JsonIgnore // Ignores this field during JSON serialization
    private final int lastPurchase; // Year of the last purchase made by the customer
    @JsonProperty("discountedValue") // Annotates the discountedValue field for JSON serialization
    private final double discountedValue; // The discounted value after applying any discounts

    // Constructor that initializes the Customer without a discounted value, defaulting to 0.0.
    public Customer(String fullName, double totalPurchase, int customerClass, int lastPurchase) {
        // Calls the overloaded constructor with discountedValue set to 0.0
        this(fullName, totalPurchase, customerClass, lastPurchase, 0.0);
    }

    @JsonCreator // Indicates this constructor should be used for JSON deserialization
    public Customer(@JsonProperty("fullName") String fullName,
                    @JsonProperty("totalPurchase") double totalPurchase,
                    @JsonProperty("customerClass") int customerClass,
                    @JsonProperty("lastPurchase") int lastPurchase,
                    @JsonProperty("discountedValue") double discountedValue) {
        // Initialize all fields with provided values
        this.fullName = fullName; // Sets the full name
        this.totalPurchase = totalPurchase; // Sets the total purchase amount
        this.customerClass = customerClass; // Sets the customer class
        this.lastPurchase = lastPurchase; // Sets the last purchase year
        this.discountedValue = discountedValue; // Sets the discounted value
    }
}