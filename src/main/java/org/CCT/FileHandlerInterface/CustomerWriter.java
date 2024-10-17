package org.CCT.FileHandlerInterface;

import org.CCT.Entity.Customer;

import java.io.IOException;
import java.util.List;

// Interface for writing customer data to a file
public interface CustomerWriter {

    // method of parent class that is used by child classes
    void writeCustomers(List<Customer> customers, String filePath) throws IOException;
}