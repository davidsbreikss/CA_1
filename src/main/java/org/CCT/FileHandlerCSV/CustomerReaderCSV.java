package org.CCT.FileHandlerCSV;

import org.CCT.Entity.Customer;
import org.CCT.Exceptions.EmptyFileException;
import org.CCT.Loggers.Logger;
import org.CCT.FileHandlerInterface.CustomerReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerReaderCSV implements CustomerReader {
    private static final int EXPECTED_FIELD_COUNT = 4; // Expected number of fields in the CSV
    private final Logger logger; // Logger for logging information and errors

    // Constructor that initializes the logger
    public CustomerReaderCSV(Logger logger) {
        this.logger = logger;
    }

    @Override
    public List<Customer> readCustomers(String filePath) throws IOException, EmptyFileException {
        List<Customer> customers = new ArrayList<>(); // List to store customer objects
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Reading customers from file: " + filePath);

        boolean isEmptyFile = true; // Flag to track if the file is empty

        // Try-with-resources to automatically close the BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line from the CSV file
            while ((line = reader.readLine()) != null) {
                line = line.trim();  // Trim whitespace from the line

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }
                isEmptyFile = false;  // Mark the file as not empty if a non-empty line is found

                String[] data = line.split(","); // Split the line into fields

                // Check the number of fields
                if (!hasValidFieldCount(data)) {
                    logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Missing customer data: " + line);
                    continue;  // Skip this line and continue
                }

                // Check for empty fields
                if (hasEmptyFields(data)) {
                    logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Empty field in customer data: " + line);
                    continue;  // Skip this line and continue
                }

                try {
                    // Creating a Customer object with the parsed data
                    Customer customer = new Customer(data[0].trim(),
                            Double.parseDouble(data[1].trim()),
                            Integer.parseInt(data[2].trim()),
                            Integer.parseInt(data[3].trim()));
                    customers.add(customer); // Add the customer to the list

                } catch (NumberFormatException e) {
                    // Log the error but continue with the next line
                    logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Invalid number format in customer data: " + line);
                }
            }

            // Check if the file was empty after reading
            if (isEmptyFile) {
                logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "This file is empty: " + filePath);
                throw new EmptyFileException("File is empty: " + filePath);
            }

        } catch (IOException e) {
            // Log IO exceptions and re-throw them
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error reading file: " + filePath + " - " + e.getMessage());
            throw e; // Re-throw IOException
        }

        // Log the successful reading of customers and return the list
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Successfully read " + customers.size() + " customers from file: " + filePath);
        return customers;
    }

    // Helper method to check if the number of fields is valid
    private boolean hasValidFieldCount(String[] data) {
        return data.length == EXPECTED_FIELD_COUNT; // Check if the field count matches the expected count
    }

    // Method to check if any field in the data array is empty
    private boolean hasEmptyFields(String[] data) {
        for (String field : data) {
            if (field.trim().isEmpty()) {
                return true; // Return true if any field is empty
            }
        }
        return false; // Return false if all fields are non-empty
    }
}
