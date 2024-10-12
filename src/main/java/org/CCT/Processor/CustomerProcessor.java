package org.CCT.Processor;

import org.CCT.Entity.Customer;
import org.CCT.Loggers.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerProcessor {

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
    private static final double[][] DISCOUNTS = {
            {0.30, 0.20, 0.10}, // Class 1
            {0.15, 0.13, 0.05}, // Class 2
            {0.03, 0.00, 0.00}  // Class 3
    };

    private final Logger logger;

    // initiate CustomerProcessor instance
    public CustomerProcessor(Logger logger) {
        this.logger = logger; // Initialize a Logger
    }

    public CustomerProcessor() {
        this.logger = new Logger(); // Initialize a default Logger
    }

    // Method to process a list of Customer objects
    public List<Customer> processCustomers(List<Customer> customers) {
        List<Customer> processedCustomers = customers.stream()
                .map(this::applyDiscount)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Customers in input file before processing: " + customers.size());
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Customers in output file after processing: " + processedCustomers.size());
        return processedCustomers;
    }

    // Method to apply discount to a Customer object
    private Optional<Customer> applyDiscount(Customer customer) {
        if (!isValidCustomer(customer)) return Optional.empty();

        double discountedValue = calculateDiscount(customer);
        return Optional.of(new Customer(
                customer.getFullName(),
                customer.getTotalPurchase(),
                customer.getCustomerClass(),
                customer.getLastPurchase(),
                discountedValue
        ));
    }

    // Method to check if a Customer object is valid based on various criteria
    private boolean isValidCustomer(Customer customer) {
        boolean isValid = true;

        // Validate and log errors
        isValid &= validateField(customer.getFullName(), this::isValidName,
                "Invalid full name for customer: " + customer.getFullName());
        isValid &= validateField(customer.getCustomerClass(), this::validateCustomerClass,
                "Invalid customer class for customer: " + customer.getFullName() + ". Class: " + customer.getCustomerClass());
        isValid &= validateField(customer.getLastPurchase(), this::validateLastPurchaseYear,
                "Invalid last purchase year for customer: " + customer.getFullName() + ". Year: " + customer.getLastPurchase());
        isValid &= validateField(customer.getTotalPurchase(), this::validateTotalPurchase,
                "Invalid total purchase for customer: " + customer.getFullName() + ". Total: " + customer.getTotalPurchase());

        if (!isValid) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Customer data is invalid: " + customer);
        }

        return isValid;
    }

    private <T> boolean validateField(T value, java.util.function.Predicate<T> validator, String errorMessage) {
        if (!validator.test(value)) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, errorMessage);
            return false;
        }
        return true;
    }

    // Method to calculate the discounted value based on customer class and last purchase year
    private double calculateDiscount(Customer customer) {
        int classIndex = customer.getCustomerClass() - 1;
        int lastPurchaseYear = customer.getLastPurchase();
        int currentYear = 2024;

        if (lastPurchaseYear == currentYear) {
            return customer.getTotalPurchase() * (1 - DISCOUNTS[classIndex][0]);
        } else if (lastPurchaseYear >= currentYear - 5) {
            return customer.getTotalPurchase() * (1 - DISCOUNTS[classIndex][1]);
        } else {
            return customer.getTotalPurchase() * (1 - DISCOUNTS[classIndex][2]);
        }
    }

    // Method to validate the full name of a customer
    private boolean isValidName(String fullName) {
        String[] nameParts = fullName.split(" ");
        return nameParts.length == 2 && nameParts[0].matches(NAME_VALIDATION) &&
                nameParts[1].matches(SECOND_NAME_VALIDATION) &&
                fullName.matches(FULL_NAME_VALIDATION);
    }

    // Method to validate the customer class value
    private boolean validateCustomerClass(int customerClass) {
        return customerClass >= MIN_CUSTOMER_CLASS && customerClass <= MAX_CUSTOMER_CLASS;
    }

    // Validate the last purchase year
    private boolean validateLastPurchaseYear(int year) {
        return year >= MIN_PURCHASE_YEAR && year <= MAX_CUSTOMER_YEAR;
    }

    // Validate the total purchase amount
    private boolean validateTotalPurchase(double totalPurchase) {
        return totalPurchase >= 0;
    }
}