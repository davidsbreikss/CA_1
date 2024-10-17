package org.CCT.FileHandlerJSON;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.CCT.Entity.Customer;
import org.CCT.Loggers.Logger;
import org.CCT.FileHandlerInterface.CustomerReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomerReaderJSON implements CustomerReader {

    private final Logger logger; // Logger for logging information and errors
    private final ObjectMapper mapper; // ObjectMapper for JSON parsing

    public CustomerReaderJSON(Logger logger) {
        this.logger = logger;
        this.mapper = new ObjectMapper(); // Initialize ObjectMapper for JSON operations
    }

    @Override
    public List<Customer> readCustomers(String filePath) throws IOException {
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Reading customers from JSON file: " + filePath);
        File file = new File(filePath);  // Directly create a File object using the provided file path

        try {
            // Check if file exists and is readable
            if (!file.exists() || !file.canRead()) {
                logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "File not found or not readable: " + filePath);
                throw new IOException("File not found or not readable: " + filePath);
            }

            // Parse JSON content using ObjectMapper
            List<Customer> customers;
            try {
                // Use ObjectMapper to read the JSON file into a List of Customer objects
                customers = mapper.readValue(file, new TypeReference<>() {});
            } catch (JsonParseException e) {
                logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Malformed JSON in file: " + filePath + " - " + e.getMessage());
                throw new IOException("Malformed JSON in file: " + filePath, e);
            } catch (JsonMappingException e) {
                logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Mapping error in JSON file: " + filePath + " - " + e.getMessage());
                throw new IOException("Mapping error in JSON file: " + filePath, e);
            }

            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Successfully read " + customers.size() + " customers from JSON file: " + filePath);
            return customers; // Return the list of customers read from the file
        } catch (IOException e) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error reading JSON file: " + filePath + " - " + e.getMessage());
            throw e; // Propagate any IOException that occurs during file reading
        }
    }
}
