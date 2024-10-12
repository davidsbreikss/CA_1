package org.CCT.FileHandlerJSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.CCT.Entity.Customer;
import org.CCT.FileHandlerInterface.CustomerWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerWriterJSON implements CustomerWriter {

    private final ObjectMapper mapper;

    public CustomerWriterJSON() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void writeCustomers(List<Customer> customers, String filePath) throws IOException {
        mapper.writeValue(new FileWriter(filePath), customers);
    }
}
