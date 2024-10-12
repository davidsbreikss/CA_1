package org.CCT.FileHandlerJSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.CCT.Entity.Customer;
import org.CCT.FileHandlerInterface.CustomerWriter;
import org.CCT.Loggers.Logger;

import java.io.BufferedWriter;
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Start the JSON array
            writer.write("[\n"); // Start with an opening bracket and new line

            for (int i = 0; i < customers.size(); i++) {
                // Serialize each customer to JSON
                String json = mapper.writeValueAsString(customers.get(i));
                writer.write("    " + json); // Write JSON string with indentation

                // Add a comma after each object except the last one
                if (i < customers.size() - 1) {
                    writer.write(",\n"); // New line after comma for better formatting
                } else {
                    writer.write("\n"); // New line after the last object
                }
            }

            // End the JSON array
            writer.write("]"); // Close the JSON array
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Successfully wrote " + customers.size() + " customers to JSON file: " + filePath);
        } catch (IOException e) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error writing to JSON file: " + filePath + " - " + e.getMessage());
            throw e;
        }
    }
}
