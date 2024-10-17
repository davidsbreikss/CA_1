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
            {0.30, 0.20, 0.10}, // Class 1 discounts for different last purchase years
            {0.15, 0.13, 0.05}, // Class 2 discounts for different last purchase years
            {0.03, 0.00, 0.00}  // Class 3 discounts for different last purchase years
    };

    private final Logger logger; // Logger instance for logging messages

    // Initiate CustomerProcessor instance with a specified Logger
    public CustomerProcessor(Logger logger) {
        this.logger = logger; // Initialize the Logger
    }

    // Default constructor which initializes a Logger with default settings
    public CustomerProcessor() {
        this.logger = new Logger(); // Initialize a default Logger
    }

    // Method to process a list of Customer objects
    public List<Customer> processCustomers(List<Customer> customers) {
        // Stream the list of customers, apply discounts, and filter valid results
        List<Customer> processedCustomers = customers.stream()
                .map(this::applyDiscount) // Apply discount to each customer
                .filter(Optional::isPresent) // Filter out empty results
                .map(Optional::get) // Get the valid Customer objects
                .collect(Collectors.toList()); // Collect the results into a list

        // Log the size of input and output customer lists
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Customers in input file before processing: " + customers.size());
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Customers in output file after processing: " + processedCustomers.size());
        return processedCustomers; // Return the processed customer list
    }

    // Method to apply discount to a Customer object
    private Optional<Customer> applyDiscount(Customer customer) {
        // Check if the customer is valid; if not, return an empty Optional
        if (!isValidCustomer(customer)) return Optional.empty();

        // Calculate the discounted value
        double discountedValue = calculateDiscount(customer);
        // Return a new Customer object with the discounted value
        return Optional.of(new Customer(
                customer.getFullName(),
                customer.getTotalPurchase(),
                customer.getCustomerClass(),
                customer.getLastPurchase(),
                discountedValue // Include discounted value in the new Customer
        ));
    }

    // Method to check if a Customer object is valid based on various criteria
    private boolean isValidCustomer(Customer customer) {
        boolean isValid = true; // Initialize validity flag

        // Validate each field and log errors if any
        isValid &= validateField(customer.getFullName(), this::isValidName,
                "Invalid full name for customer: " + customer.getFullName());
        isValid &= validateField(customer.getCustomerClass(), this::validateCustomerClass,
                "Invalid customer class for customer: " + customer.getFullName() + ". Class: " + customer.getCustomerClass());
        isValid &= validateField(customer.getLastPurchase(), this::validateLastPurchaseYear,
                "Invalid last purchase year for customer: " + customer.getFullName() + ". Year: " + customer.getLastPurchase());
        isValid &= validateField(customer.getTotalPurchase(), this::validateTotalPurchase,
                "Invalid total purchase for customer: " + customer.getFullName() + ". Total: " + customer.getTotalPurchase());

        // Log an error message if the customer data is invalid
        if (!isValid) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Customer data is invalid: " + customer);
        }

        return isValid; // Return the validity status
    }

    // Generic method to validate a field using a predicate
    private <T> boolean validateField(T value, java.util.function.Predicate<T> validator, String errorMessage) {
        return validator.test(value); // Validate the field using the provided predicate
    }

    // Method to calculate the discounted value based on customer class and last purchase year
    private double calculateDiscount(Customer customer) {
        int classIndex = customer.getCustomerClass() - 1; // Get the index for discount based on customer class
        int lastPurchaseYear = customer.getLastPurchase(); // Get the last purchase year
        int currentYear = 2024; // Define current year

        // Determine the discount based on last purchase year
        if (lastPurchaseYear == currentYear) {
            return customer.getTotalPurchase() * (1 - DISCOUNTS[classIndex][0]); // Current year discount
        } else if (lastPurchaseYear >= currentYear - 5) {
            return customer.getTotalPurchase() * (1 - DISCOUNTS[classIndex][1]); // Recent purchase discount
        } else {
            return customer.getTotalPurchase() * (1 - DISCOUNTS[classIndex][2]); // Older purchase discount
        }
    }

    // Method to validate the full name of a customer
    private boolean isValidName(String fullName) {
        String[] nameParts = fullName.split(" "); // Split the full name into parts
        // Check if it has exactly two parts and validate each part
        return nameParts.length == 2 && nameParts[0].matches(NAME_VALIDATION) &&
                nameParts[1].matches(SECOND_NAME_VALIDATION) &&
                fullName.matches(FULL_NAME_VALIDATION);
    }

    // Method to validate the customer class value
    private boolean validateCustomerClass(int customerClass) {
        return customerClass >= MIN_CUSTOMER_CLASS && customerClass <= MAX_CUSTOMER_CLASS; // Validate class range
    }

    // Validate the last purchase year
    private boolean validateLastPurchaseYear(int year) {
        return year >= MIN_PURCHASE_YEAR && year <= MAX_CUSTOMER_YEAR; // Validate year range
    }

    // Validate the total purchase amount
    private boolean validateTotalPurchase(double totalPurchase) {
        return totalPurchase >= 0; // Ensure total purchase is non-negative
    }
}
