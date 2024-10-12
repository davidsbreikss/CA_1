package org.CCT.FileHandlerCSV;

import org.CCT.Entity.Customer;
import org.CCT.Loggers.Logger;
import org.CCT.FileHandlerInterface.CustomerReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerReaderCSV implements CustomerReader {

    private static final int CUSTOMER_FIELDS_COUNT = 4;

    private final Logger logger;

    public CustomerReaderCSV(Logger logger) {
        this.logger = logger;
    }

    @Override
    public List<Customer> readCustomers(String filePath) throws IOException {
        List<Customer> customers = new ArrayList<>();
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Reading customers from file: " + filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == CUSTOMER_FIELDS_COUNT) {
                    try {
                        Customer customer = new Customer(data[0].trim(),
                                Double.parseDouble(data[1].trim()),
                                Integer.parseInt(data[2].trim()),
                                Integer.parseInt(data[3].trim()));
                        customers.add(customer);
                    } catch (NumberFormatException e) {
                        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Invalid data format in line: " + line);
                    }
                } else {
                    logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Incorrect number of fields in line: " + line);
                }
            }
        } catch (Exception e) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error reading file: " + filePath + " - " + e.getMessage());
            throw e; // rethrow after logging
        }
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Successfully read " + customers.size() + " customers from file: " + filePath);
        return customers;
    }
}