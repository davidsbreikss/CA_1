package org.CCT.FileHandlerTxt;

import org.CCT.Entity.Customer;
import org.CCT.FileHandlerInterface.CustomerWriter;
import org.CCT.Loggers.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerWriterTxt implements CustomerWriter {

    private final Logger logger; // Logger for logging information and errors

    public CustomerWriterTxt(Logger logger) {
        this.logger = logger; // Initialize logger
    }

    // Method to write a list of Customer objects to a specified file
    @Override
    public void writeCustomers(List<Customer> customers, String filePath) throws IOException {

        // Check if the customer list is empty
        if (customers.isEmpty()) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Cannot write empty customer list to file: " + filePath);
            throw new IOException("Cannot write empty customer list to file: " + filePath);
        }

        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Writing " + customers.size() + " customers to file: " + filePath);

        // Use BufferedWriter to write to the file efficiently
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Iterate through each customer in the list
            for (Customer customer : customers) {
                // Format the customer data into a string and write it to the file
                writer.write(formatCustomer(customer)); // Write the formatted customer data
                writer.newLine(); // Write a newline after each customer's data
            }
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Successfully wrote " + customers.size() + " customers to file: " + filePath);
        } catch (IOException e) {
            // Log error if writing fails and rethrow the exception
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error writing to file: " + filePath + " - " + e.getMessage());
            throw e;  // Rethrow the exception after logging it
        }
    }

    // Method to format a Customer object into a string representation
    private String formatCustomer(Customer customer) {
        // Create a formatted string with the customer's full name and discounted value
        // The discounted value is formatted to two decimal places
        return String.format("%s\n%.2f",
                customer.getFullName(),         // Full name of the customer
                customer.getDiscountedValue()); // Discounted value of the customer
    }
}
