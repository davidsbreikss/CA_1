package org.CCT.FileHandlerJSON;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.CCT.Entity.Customer;
import org.CCT.Loggers.Logger;
import org.CCT.FileHandlerInterface.CustomerReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CustomerReaderJSON implements CustomerReader {

    private final Logger logger;
    private final ObjectMapper mapper;

    public CustomerReaderJSON(Logger logger) {
        this.logger = logger;
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<Customer> readCustomers(String filePath) throws IOException {
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Reading customers from JSON file: " + filePath);
        File file = new File(filePath);  // Create the File object once
        try {
            if (!file.exists() || !file.canRead()) {
                logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "File not found or not readable: " + filePath);
                throw new IOException("File not found or not readable: " + filePath);
            }
            List<Customer> customers = mapper.readValue(file, new TypeReference<>() {});  // Reuse the File object
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Successfully read " + customers.size() + " customers from JSON file: " + filePath);
            return customers;
        } catch (IOException e) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error reading JSON file: " + filePath + " - " + e.getMessage());
            throw e;
        }
    }
}