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

    // Fields for customers information
    @JsonProperty("fullName")
    private final String fullName;
    @JsonIgnore
    private final double totalPurchase;
    @JsonIgnore
    private final int customerClass;
    @JsonIgnore
    private final int lastPurchase;
    @JsonProperty("discountedValue")
    private final double discountedValue;

    // Constructor that initializes the Customer without a discounted value, defaulting to 0.0.
    public Customer(String fullName, double totalPurchase, int customerClass, int lastPurchase) {
        // Calls the overloaded constructor with discountedValue set to 0.0
        this(fullName, totalPurchase, customerClass, lastPurchase, 0.0);
    }

    @JsonCreator
    public Customer(@JsonProperty("fullName") String fullName,
                    @JsonProperty("totalPurchase") double totalPurchase,
                    @JsonProperty("customerClass") int customerClass,
                    @JsonProperty("lastPurchase") int lastPurchase,
                    @JsonProperty("discountedValue") double discountedValue) {
        this.fullName = fullName;
        this.totalPurchase = totalPurchase;
        this.customerClass = customerClass;
        this.lastPurchase = lastPurchase;
        this.discountedValue = discountedValue;
    }
}