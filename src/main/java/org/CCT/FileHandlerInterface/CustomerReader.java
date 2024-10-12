package org.CCT.FileHandlerInterface;

import org.CCT.Entity.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerReader {

    List<Customer> readCustomers(String filePath) throws IOException;
}
