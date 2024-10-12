package org.CCT.FileHandlerCSV;

import org.CCT.Entity.Customer;
import org.CCT.FileHandlerInterface.CustomerWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerWriterCSV implements CustomerWriter {
    @Override
    public void writeCustomers(List<Customer> customers, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Customer customer : customers) {
                writer.write(String.format("%s,%s,%d,%d",
                        customer.getFullName(),
                        customer.getTotalPurchase(),
                        customer.getCustomerClass(),
                        customer.getLastPurchase()));
                writer.newLine();
            }
        }
    }
}