package org.CCT.FileHandlerJSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.CCT.Entity.Customer;
import org.CCT.FileHandlerInterface.CustomerWriter;
import org.CCT.Loggers.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerWriterJSON implements CustomerWriter {
    private final ObjectMapper mapper; // ObjectMapper for JSON serialization
    private final Logger logger; // Logger for logging information and errors

    public CustomerWriterJSON(Logger logger) {
        this.logger = logger;
        this.mapper = new ObjectMapper(); // Initialize ObjectMapper for JSON operations
    }

    @Override
    public void writeCustomers(List<Customer> customers, String filePath) throws IOException {
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Writing customers to JSON file: " + filePath);

        // Check if the customer list is empty
        if (customers.isEmpty()) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.WARN, "No customers to write. The list is empty.");
            throw new IllegalArgumentException("Cannot write to JSON file. Customer list is empty: " + filePath);
        }

        try {
            // Directly write the list of customers to the JSON file using ObjectMapper
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), customers);
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Successfully wrote " + customers.size() + " customers to JSON file: " + filePath);
        } catch (IOException e) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error writing to JSON file: " + filePath + " - " + e.getMessage());
            throw e; // Propagate any IOException that occurs during file writing
        }
    }
}
