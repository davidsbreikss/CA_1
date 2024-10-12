package org.CCT.FileHandlerTxt;

import org.CCT.Entity.Customer;
import org.CCT.FileHandlerInterface.CustomerReader;
import org.CCT.Loggers.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerReaderTxt implements CustomerReader {

    private final Logger logger;

    public CustomerReaderTxt(Logger logger) {
        this.logger = logger;
    }

    public CustomerReaderTxt() {
        this.logger = new Logger();
    }

    // Method to read customer data from a file and return a list of Customer objects
    @Override
    public List<Customer> readCustomers(String filePath) throws IOException {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<String> customerDataLines = new ArrayList<>();
            int customerCount = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    customerDataLines.add(line.trim());
                }

                // Once we have 4 lines, we process them as one customer
                if (customerDataLines.size() == 4) {
                    Customer customer = parseCustomer(customerDataLines);
                    if (customer != null) {
                        customers.add(customer);
                        customerCount++;
                        logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Customer " + customerCount + " processed: " + customer);
                    }
                    customerDataLines.clear();  // Reset for the next customer
                }
            }
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.INFO, "Total customers read: " + customerCount);
        }
        return customers;
    }

    // Method to parse a block of customer data and create a Customer object
    private Customer parseCustomer(List<String> customerDataLines) {
        try {
            String fullName = customerDataLines.get(0);
            double totalPurchase = Double.parseDouble(customerDataLines.get(1));
            int customerClass = Integer.parseInt(customerDataLines.get(2));
            int lastPurchaseYear = Integer.parseInt(customerDataLines.get(3));

            return new Customer(fullName, totalPurchase, customerClass, lastPurchaseYear);
        } catch (NumberFormatException e) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Error parsing customer data: " + e.getMessage() + " Data: " + customerDataLines);
            return null;
        }
    }
}