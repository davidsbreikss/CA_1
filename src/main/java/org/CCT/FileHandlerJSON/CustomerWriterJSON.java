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
    private final ObjectMapper mapper;
    private final Logger logger;

    public CustomerWriterJSON(Logger logger) {
        this.logger = logger;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void writeCustomers(List<Customer> customers, String filePath) throws IOException {
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Writing customers to JSON file: " + filePath);
        try {
            // Directly write the list of customers to the JSON file
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), customers);
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Successfully wrote " + customers.size() + " customers to JSON file: " + filePath);
        } catch (IOException e) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error writing to JSON file: " + filePath + " - " + e.getMessage());
            throw e;
        }
    }
}