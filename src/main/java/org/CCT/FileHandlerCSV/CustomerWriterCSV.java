package org.CCT.FileHandlerCSV;

import org.CCT.Entity.Customer;
import org.CCT.FileHandlerInterface.CustomerWriter;
import org.CCT.Loggers.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerWriterCSV implements CustomerWriter {

    private final Logger logger;

    public CustomerWriterCSV(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void writeCustomers(List<Customer> customers, String filePath) throws IOException {
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Writing " + customers.size() + " customers to file: " + filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Customer customer : customers) {
                writer.write(String.format("%s,%.2f,%d,%d",
                        customer.getFullName(),
                        customer.getTotalPurchase(),
                        customer.getCustomerClass(),
                        customer.getLastPurchase()));
                writer.newLine();
            }
        } catch (IOException e) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error writing to file: " + filePath + " - " + e.getMessage());
            throw e;
        }
        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Successfully wrote customers to file: " + filePath);
    }
}