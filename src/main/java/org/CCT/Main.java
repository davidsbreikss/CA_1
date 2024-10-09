package org.CCT;

import org.CCT.Entity.Customer;
import org.CCT.FileHandler.CustomerReader;
import org.CCT.FileHandler.CustomerWriter;
import org.CCT.Processor.CustomerProcessor;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String inputFilePath = getFilePathFromResources("customers.txt");
        String outputFilePath = getFilePathFromResources("output/customerDiscount.txt");

        // Ensure both paths are valid
        if (inputFilePath == null || outputFilePath == null) {
            System.err.println("Could not load file paths from resources.");
            return;
        }

        // Create instances of the CustomerReader, CustomerProcessor, and CustomerWriter
        CustomerReader customerReader = new CustomerReader();
        CustomerProcessor customerProcessor = new CustomerProcessor();
        CustomerWriter customerWriter = new CustomerWriter();

        try {
            // Step 1: Read customers from the input file
            List<Customer> customers = customerReader.readCustomers(inputFilePath);

            // Step 2: Process the customers (apply discounts)
            List<Customer> processedCustomers = customerProcessor.processCustomers(customers);

            // Step 3: Write the processed customers to the output file
            customerWriter.writeCustomers(processedCustomers, outputFilePath);

            System.out.println("Customers processed and written to: " + outputFilePath);
        } catch (IOException e) {
            // Handle any exceptions that occur during file I/O
            System.err.println("Error reading or writing customer data: " + e.getMessage());
        }

    }

    private static String getFilePathFromResources(String fileName) {
        // Use the class loader to locate the resource
        URL resource = Main.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            System.err.println("File not found in resources: " + fileName);
            return null;
        }

        try {
            // Convert URL to URI and then to a valid file path
            return Paths.get(resource.toURI()).toString();
        } catch (Exception e) {
            System.err.println("Error converting file path: " + e.getMessage());
            return null;
        }
    }
}