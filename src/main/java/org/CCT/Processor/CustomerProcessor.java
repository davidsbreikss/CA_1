package org.CCT.Processor;

import org.CCT.Entity.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerProcessor {
    // File path where customer data is stored
    private static final String filePath = "resources/customer.txt";

    // Regular expressions for validating names
    private static final String NAME_VALIDATION = "[a-zA-Z]+"; // Only letters for first name
    private static final String SECOND_NAME_VALIDATION = "[a-zA-Z0-9]+"; // Letters and numbers for second name
    private static final String FULL_NAME_VALIDATION = "[a-zA-Z]+\\s[a-zA-Z0-9]+"; // Full name format validation

    // Constants for customer class and purchase year limits
    private static final int MIN_CUSTOMER_CLASS = 1; // Minimum value for customer class
    private static final int MAX_CUSTOMER_CLASS = 3; // Maximum value for customer class
    private static final int MIN_PURCHASE_YEAR = 1990; // Minimum valid year for last purchase
    private static final int MAX_CUSTOMER_YEAR = 2024; // Maximum valid year for last purchase

    // Discount percentages based on customer class and last purchase year
    private static final double DISCOUNT_30_PERCENTAGE = 0.30;
    private static final double DISCOUNT_20_PERCENTAGE = 0.20;
    private static final double DISCOUNT_15_PERCENTAGE = 0.15;
    private static final double DISCOUNT_10_PERCENTAGE = 0.10;
    private static final double DISCOUNT_13_PERCENTAGE = 0.13;
    private static final double DISCOUNT_5_PERCENTAGE = 0.05;
    private static final double DISCOUNT_3_PERCENTAGE = 0.03;

    // Method to process a list of Customer objects
    public List<Customer> processCustomers(List<Customer> customers) {
        // Filter valid customers and apply discounts
        List<Customer> processedCustomers = customers.stream()
                .filter(this::isValidCustomer) // Validate each customer
                .map(this::applyDiscount) // Apply discounts to valid customers
                .collect(Collectors.toList()); // Collect the results into a list

        // Print the number of customers before and after processing
        System.out.println("Customers before processing: " + customers.size());
        System.out.println("Customers after processing: " + processedCustomers.size());
        return processedCustomers; // Return the list of processed customers
    }

    // Method to apply discount to a Customer object
    private Customer applyDiscount(Customer customer) {
        double discountedValue = customerDiscount(customer); // Calculate discounted value
        // Return a new Customer object with the updated discounted value
        return new Customer(
                customer.getFullName(),
                customer.getTotalPurchase(),
                customer.getCustomerClass(),
                customer.getLastPurchase(),
                discountedValue
        );
    }

    // Method to check if a Customer object is valid based on various criteria
    private boolean isValidCustomer(Customer customer) {
        // Validate customer properties and check if the customer is valid
        boolean isValid = isValidName(customer.getFullName()) &&
                validateCustomerClass(customer.getCustomerClass()) &&
                validateLastPurchaseYear(customer.getLastPurchase()) &&
                validateTotalPurchase(customer.getTotalPurchase());

        // Print an error message if the customer is invalid
        if (!isValid) {
            System.out.println("Invalid customer: " + customer);
        }

        return isValid; // Return the validation result
    }

    // Method to calculate the discounted value based on customer class and last purchase year
    private double customerDiscount(Customer customer) {
        int customerClass = customer.getCustomerClass();
        int lastPurchaseYear = customer.getLastPurchase();
        int currentYear = 2024; // Set the current year

        double discountPercentage = 0.0; // Initialize discount percentage

        // Determine discount percentage based on last purchase year and customer class
        if (lastPurchaseYear == currentYear) {
            discountPercentage = (customerClass == 1) ? DISCOUNT_30_PERCENTAGE :
                    (customerClass == 2) ? DISCOUNT_15_PERCENTAGE :
                            (customerClass == 3) ? DISCOUNT_3_PERCENTAGE : 0.0;
        } else if (lastPurchaseYear >= currentYear - 5) {
            discountPercentage = (customerClass == 1) ? DISCOUNT_20_PERCENTAGE :
                    (customerClass == 2) ? DISCOUNT_13_PERCENTAGE : 0.0;
        } else {
            discountPercentage = (customerClass == 1) ? DISCOUNT_10_PERCENTAGE :
                    (customerClass == 2) ? DISCOUNT_5_PERCENTAGE : 0.0;
        }

        // Calculate and return the discounted total purchase value
        return customer.getTotalPurchase() * (1 - discountPercentage);
    }

    // Method to validate the full name of a customer
    private boolean isValidName(String fullName) {
        String[] nameParts = fullName.split(" "); // Split the full name into parts
        // Check if the name has exactly two parts (first name and second name)
        if (nameParts.length != 2) {
            return false; // Invalid name format
        }
        String firstName = nameParts[0];
        String secondName = nameParts[1];
        // Validate first name, second name, and full name format
        return firstName.matches(NAME_VALIDATION) &&
                secondName.matches(SECOND_NAME_VALIDATION) &&
                fullName.matches(FULL_NAME_VALIDATION);
    }

    // Method to validate the customer class value
    private boolean validateCustomerClass(int customerClass) {
        // Check if the customer class is within the valid range
        return customerClass >= MIN_CUSTOMER_CLASS && customerClass <= MAX_CUSTOMER_CLASS;
    }

    // Method to validate the last purchase year
    private boolean validateLastPurchaseYear(int year) {
        // Check if the last purchase year is within the valid range
        return year >= MIN_PURCHASE_YEAR && year <= MAX_CUSTOMER_YEAR;
    }

    // Method to validate the total purchase amount
    private boolean validateTotalPurchase(double totalPurchase) {
        // Ensure total purchase is non-negative
        return totalPurchase >= 0;
    }
}
