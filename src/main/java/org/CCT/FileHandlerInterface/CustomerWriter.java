package org.CCT.FileHandlerInterface;

import org.CCT.Entity.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerWriter {
    void writeCustomers(List<Customer> customers, String filePath) throws IOException;
}