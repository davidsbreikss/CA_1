package org.CCT.FileHandlerTxt;

import org.CCT.Entity.Customer;
import org.CCT.Exceptions.EmptyFileException;
import org.CCT.FileHandlerInterface.CustomerReader;
import org.CCT.Loggers.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerReaderTxt implements CustomerReader {

    private static final int EXPECTED_FIELD_COUNT = 4; // Expected number of fields per customer
    private final Logger logger; // Logger for logging information and errors

    public CustomerReaderTxt(Logger logger) {
        this.logger = logger; // Initialize logger
    }

    // Method to read customer data from a file and return a list of Customer objects
    @Override
    public List<Customer> readCustomers(String filePath) throws IOException, EmptyFileException {
        List<Customer> customers = new ArrayList<>(); // List to hold Customer objects
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Reading customers from file: " + filePath);
        boolean isEmptyFile = true; // Flag to check if the file is empty

        // Try-with-resources to ensure the BufferedReader is closed automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line; // Variable to hold each line read from the file
            List<String> customerDataLines = new ArrayList<>(); // Temporary list to store lines for one customer
            int customerCount = 0; // Counter for the number of customers processed

            // Read lines from the file
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim whitespace from the line
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                isEmptyFile = false; // File is not empty

                customerDataLines.add(line); // Add the line to the customer data

                // Once we have 4 lines, we process them as one customer
                if (customerDataLines.size() == EXPECTED_FIELD_COUNT) {
                    Customer customer = parseCustomer(customerDataLines); // Parse customer data
                    if (customer != null) { // If customer is valid
                        customers.add(customer); // Add to customers list
                        customerCount++; // Increment customer count
                        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Customer " + customerCount + " processed: " + customer);
                    }
                    customerDataLines.clear(); // Reset for the next customer
                }
            }

            // Check if there are any remaining lines that form a complete customer
            if (!customerDataLines.isEmpty()) {
                logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Incomplete customer data at end of file: " + customerDataLines);
            }

            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Total customers read: " + customerCount);
        }

        // Throw an exception if the file was empty
        if (isEmptyFile) {
            throw new EmptyFileException("The file is empty: " + filePath);
        }

        return customers; // Return the list of customers read from the file
    }

    // Method to parse a block of customer data and create a Customer object
    private Customer parseCustomer(List<String> customerDataLines) {
        try {
            // Extract customer data from the list of lines
            String fullName = customerDataLines.get(0); // First line is the full name
            double totalPurchase = Double.parseDouble(customerDataLines.get(1)); // Second line is the total purchase
            int customerClass = Integer.parseInt(customerDataLines.get(2)); // Third line is the customer class
            int lastPurchaseYear = Integer.parseInt(customerDataLines.get(3)); // Fourth line is the last purchase year

            // Create and return a new Customer object
            return new Customer(fullName, totalPurchase, customerClass, lastPurchaseYear);
        } catch (NumberFormatException e) {
            // Log error if parsing fails and return null
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error parsing customer data: " + e.getMessage() + " Data: " + customerDataLines);
            return null;
        }
    }
}
