package org.CCT.FileHandlerInterface;

import org.CCT.Entity.Customer;
import org.CCT.Exceptions.CustomerDataException;
import org.CCT.Exceptions.EmptyFileException;

import java.io.IOException;
import java.util.List;

public interface CustomerReader {

    List<Customer> readCustomers(String filePath) throws IOException, CustomerDataException, EmptyFileException;
}
