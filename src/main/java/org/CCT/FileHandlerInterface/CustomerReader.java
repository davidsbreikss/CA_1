package org.CCT.FileHandlerInterface;

import org.CCT.Entity.Customer;
import org.CCT.Exceptions.CustomerDataException;
import org.CCT.Exceptions.EmptyFileException;

import java.io.IOException;
import java.util.List;

// Interface for reading customer data from a file
public interface CustomerReader {

    // method of parent class that is used by child classes
    List<Customer> readCustomers(String filePath) throws IOException, CustomerDataException, EmptyFileException;
}
