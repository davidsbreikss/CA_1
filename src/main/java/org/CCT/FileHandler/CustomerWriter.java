package org.CCT.FileHandler;
import org.CCT.Entity.Customer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerWriter {

    // Method to write a list of Customer objects to a specified file
    public void writeCustomers(List<Customer> customers, String filePath) throws IOException {
        // Use BufferedWriter to write to the file efficiently
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Iterate through each customer in the list
            for (Customer customer : customers) {
                // Format the customer data into a string and write it to the file
                writer.write(formatCustomer(customer));
                // Write a newline after each customer's data
                writer.newLine();
            }
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