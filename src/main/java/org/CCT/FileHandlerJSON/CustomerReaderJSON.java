package org.CCT.FileHandlerJSON;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.CCT.Entity.Customer;
import org.CCT.Loggers.Logger;
import org.CCT.FileHandlerInterface.CustomerReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CustomerReaderJSON implements CustomerReader {

    private final Logger logger;
    private final ObjectMapper mapper;

    public CustomerReaderJSON(Logger logger) {
        this.logger = logger;
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<Customer> readCustomers(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), new TypeReference<>() {
        });
    }
}
