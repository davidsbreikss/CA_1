package org.CCT.FileHandler;

import org.CCT.Entity.Customer;
import org.CCT.Loggers.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerReader {

    private final Logger logger;

    public CustomerReader(Logger logger) {
        this.logger = logger;
    }

    public CustomerReader() {
        this.logger = new Logger();
    }

    // Method to read customer data from a file and return a list of Customer objects
    public List<Customer> readCustomers(String filePath) throws IOException {
        // List to hold the customer objects read from the file
        List<Customer> customers = new ArrayList<>();

        // Use BufferedReader to read the file efficiently line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder customerData = new StringBuilder(); // Stores the current customer data
            int lineCount = 0;   // Tracks the number of lines read for each customer
            int customerCount = 0; // Tracks the total number of customers processed

            // Loop to read the file line by line
            while ((line = reader.readLine()) != null) {
                // Display the current line being read
                logger.log(this.getClass().getSimpleName(), "INFO", "Read line: " + line);

                // Append the current line to the customerData buffer
                customerData.append(line).append("\n");
                lineCount++; // Increment the line counter for the customer

                // When 4 lines have been read
                if (lineCount == 4) {
                    // Parse the customer data and create a Customer object
                    Customer customer = parseCustomer(customerData.toString());
                    if (customer != null) {
                        // Add the valid customer object to the list
                        customers.add(customer);
                        customerCount++; // Increment the customer count
                        // Display each customer that has been read
                        logger.log(this.getClass().getSimpleName(), "INFO", "Customer has been read: " + customer);
                    }
                    // Reset the customerData buffer and line counter for the next customer
                    customerData = new StringBuilder();
                    lineCount = 0;
                }
            }
            // Display the total number of customers read
            logger.log(this.getClass().getSimpleName(), "INFO", "Total customers: " + customers.size());
        }

        return customers; // Return the list of customers
    }

    // Method to parse a block of customer data and create a Customer object
    private Customer parseCustomer(String customerData) {
        // Split the customer data block into individual lines
        String[] lines = customerData.split("\n");
        // If there are not exactly 4 lines, the data format is invalid
        if (lines.length != 4) {
            // Print an error message and return null
            logger.log(this.getClass().getSimpleName(), "ERROR", "Invalid customer data format: " + customerData);
            return null;
        }

        try {
            // Parse each line to extract the customer attributes
            String fullName = lines[0].trim(); // First line contains the full name
            double totalPurchase = Double.parseDouble(lines[1].trim()); // Second line contains total purchase amount
            int customerClass = Integer.parseInt(lines[2].trim()); // Third line contains the customer class
            int lastPurchaseYear = Integer.parseInt(lines[3].trim()); // Fourth line contains the last purchase year

            // Return a new Customer object initialized with the parsed data
            return new Customer(fullName, totalPurchase, customerClass, lastPurchaseYear);
        } catch (NumberFormatException e) {
            // Handle any parsing errors (invalid number formats)
            logger.log(this.getClass().getSimpleName(), "ERROR", "Error parsing customer data: " + e.getMessage() + "\nData: " + customerData);
            return null; // Return null if there was an error
        }
    }
}