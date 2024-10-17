package FileHandlerJSON;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.CCT.Entity.Customer;
import org.CCT.FileHandlerJSON.CustomerWriterJSON;
import org.CCT.Loggers.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerWriterJSONTest {

    private String filePath;  // Variable to hold the file path for testing

    private Logger logger;    // Mocked Logger instance for logging
    private ObjectMapper objectMapper; // Mocked ObjectMapper for JSON processing
    private CustomerWriterJSON writer; // Instance of the CustomerWriterJSON to be tested

    // Setup method to initialize the mocks and writer before each test
    @BeforeEach
    void setUp() {
        logger = Mockito.mock(Logger.class);
        objectMapper = Mockito.mock(ObjectMapper.class);
        writer = new CustomerWriterJSON(logger);
    }

    // Test for successfully writing valid customer data to a JSON file
    @Test
    void testWriteValidCustomers() throws IOException {
        // Given: a valid list of customers and a file path
        List<Customer> customers = List.of(
                new Customer("John Doe", 20.00, 2, 2023),
                new Customer("Jane Smith", 25.00, 1, 2022)
        );
        filePath = "src/test/resources/JSONWriter/customers.json"; // Set the file path for the output

        // Write the customers to the specified JSON file
        writer.writeCustomers(customers, filePath);

        // Read the written customers back from the JSON file for verification
        ObjectMapper objectMapper = new ObjectMapper();
        List<Customer> writtenCustomers = objectMapper.readValue(new File(filePath), new TypeReference<>() {});

        // Assertions to verify the written customers match the original customers
        assertEquals(customers.size(), writtenCustomers.size(), "The number of written customers should match.");
        assertEquals(customers.get(0).getFullName(), writtenCustomers.get(0).getFullName(), "First customer's name should match.");
        assertEquals(customers.get(1).getFullName(), writtenCustomers.get(1).getFullName(), "Second customer's name should match.");
    }

    // Test for handling attempts to write an empty list of customers
    @Test
    void testWriteEmptyListThrowsException() {
        // Given: an empty list of customers
        List<Customer> emptyCustomerList = new ArrayList<>();
        filePath = "src/test/resources/JSONWriter/customers.json"; // Set the file path for the output

        // When: attempting to write the empty customer list
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            writer.writeCustomers(emptyCustomerList, filePath);
        });

        // Then: assert that an IllegalArgumentException is thrown with the expected message
        assertEquals("Cannot write to JSON file. Customer list is empty: " + filePath, exception.getMessage());
    }
}
