package org.CCT.FileHandlerCSV;

import org.CCT.Entity.Customer;
import org.CCT.FileHandlerInterface.CustomerWriter;
import org.CCT.Loggers.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerWriterCSV implements CustomerWriter {
    private final Logger logger; // Logger for logging information and errors

    // Constructor that initializes the logger
    public CustomerWriterCSV(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void writeCustomers(List<Customer> customers, String filePath) throws IOException {
        // Check if the customer list is empty
        if (customers.isEmpty()) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Cannot write empty customer list to file: " + filePath);
            throw new IOException("Cannot write empty customer list to file: " + filePath);
        }

        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Writing " + customers.size() + " customers to file: " + filePath);
        // Try-with-resources to automatically close the BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write each customer to the file
            for (Customer customer : customers) {
                writer.write(formatCustomer(customer)); // Format and write customer data
                writer.newLine(); // Write a new line after each customer
            }
        } catch (IOException e) {
            // Log IO exceptions and re-throw them
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error writing to file: " + filePath + " - " + e.getMessage());
            throw e;
        }
        // Log successful writing of customers
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Successfully wrote customers to file: " + filePath);
    }

    // Helper method to format a customer object into a CSV string
    private String formatCustomer(Customer customer) {
        // Create a formatted string with the customer's full name and discounted value
        // The discounted value is formatted to two decimal places
        return String.format("%s,%.2f",
                customer.getFullName(),         // Full name of the customer
                customer.getDiscountedValue()); // Discounted value of the customer
    }
}
