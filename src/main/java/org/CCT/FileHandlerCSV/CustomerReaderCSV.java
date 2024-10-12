package org.CCT.FileHandlerCSV;

import org.CCT.Entity.Customer;
import org.CCT.Loggers.Logger;
import org.CCT.FileHandlerInterface.CustomerReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerReaderCSV implements CustomerReader {

    private final Logger logger;

    public CustomerReaderCSV(Logger logger) {
        this.logger = logger;
    }

    @Override
    public List<Customer> readCustomers(String filePath) throws IOException {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if(data.length ==4){
                    Customer customer = new Customer(data[0].trim(),
                            Double.parseDouble(data[1].trim()),
                            Integer.parseInt(data[2].trim()),
                            Integer.parseInt(data[3].trim()));
                    customers.add(customer);
                }
            }
        }
        return customers;
    }
}
