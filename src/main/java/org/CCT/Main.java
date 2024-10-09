package org.CCT;

import org.CCT.Entity.Customer;
import org.CCT.FileHandler.CustomerReader;
import org.CCT.FileHandler.CustomerWriter;
import org.CCT.Processor.CustomerProcessor;

import java.io.*;
import java.util.List;

public class Main {

    private static final String INPUT_FILE_PATH = "resources/customers.txt";
    private static final String OUTPUT_FILE_PATH = "resources/customerDiscount.txt";
    public static void main(String[] args) {

        try {
            String currentDir = System.getProperty("user.dir");
            String absoluteInputPath = currentDir + File.separator + INPUT_FILE_PATH;
            String absoluteOutputPath = currentDir + File.separator + OUTPUT_FILE_PATH;

            System.out.println("Reading from: " + absoluteInputPath);

            // Print file contents
            System.out.println("File contents:");
            try (BufferedReader reader = new BufferedReader(new FileReader(absoluteInputPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            System.out.println("End of file contents");

            CustomerReader reader = new CustomerReader();
            CustomerWriter writer = new CustomerWriter();
            CustomerProcessor processor = new CustomerProcessor();

            List<Customer> customers = reader.readCustomers(absoluteInputPath);
            System.out.println("Customers read: " + customers.size());

            List<Customer> processedCustomers = processor.processCustomers(customers);
            System.out.println("Customers processed: " + processedCustomers.size());

            System.out.println("Writing to: " + absoluteOutputPath);
            writer.writeCustomers(processedCustomers, absoluteOutputPath);

            System.out.println("Data processed successfully");
            System.out.println("Total customer data processed: " + processedCustomers.size());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}