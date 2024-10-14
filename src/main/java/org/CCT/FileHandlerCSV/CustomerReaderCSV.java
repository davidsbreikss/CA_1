package org.CCT.FileHandlerCSV;

import org.CCT.Entity.Customer;
import org.CCT.Exceptions.CustomerDataException;
import org.CCT.Exceptions.EmptyFileException;
import org.CCT.Loggers.Logger;
import org.CCT.FileHandlerInterface.CustomerReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerReaderCSV implements CustomerReader {

    private static final int CUSTOMER_FIELDS_COUNT = 4;

    private final Logger logger;

    public CustomerReaderCSV(Logger logger) {
        this.logger = logger;
    }

    @Override
    public List<Customer> readCustomers(String filePath) throws IOException, CustomerDataException, EmptyFileException {

        List<Customer> customers = new ArrayList<>();
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Reading customers from file: " + filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isEmptyFile = true;// Track if the file is empty
            boolean allDataCorrupted = true;

            while ((line = reader.readLine()) != null) {
                isEmptyFile = false;  // The file is not empty if we reach here
                String[] data = line.split(",");
                // Check for missing or incorrect number of fields and log error if invalid
                if (data.length != CUSTOMER_FIELDS_COUNT) {
                    logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Invalid customer data (incorrect number of fields): " + line);
                    continue; // Skip this line and go to the next
                }

                try {
                    // Try to create a Customer object
                    Customer customer = new Customer(data[0].trim(),
                            Double.parseDouble(data[1].trim()),
                            Integer.parseInt(data[2].trim()),
                            Integer.parseInt(data[3].trim()));
                    customers.add(customer);
                    allDataCorrupted = false;
                } catch (NumberFormatException e) {
                    // Log the error but continue with the next line
                    logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Invalid number format in customer data: " + line);
                    continue;  // Skip this line and continue
                }
            }

            if (isEmptyFile) {
                throw new EmptyFileException("File is empty: " + filePath);
            }

            if (allDataCorrupted) {
                throw new CustomerDataException("All data is corrupted in the file: " + filePath);
            }

        } catch (IOException e) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error reading file: " + filePath + " - " + e.getMessage());
            throw e; // Re-throw IOException
        }

        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Successfully read " + customers.size() + " customers from file: " + filePath);
        return customers;
    }
}