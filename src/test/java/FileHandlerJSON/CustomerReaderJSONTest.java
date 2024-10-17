package FileHandlerJSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.CCT.Entity.Customer;
import org.CCT.FileHandlerJSON.CustomerReaderJSON;
import org.CCT.Loggers.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerReaderJSONTest {

    private String filePath;  // Variable to hold the file path for testing

    private Logger logger;    // Mocked Logger instance for logging
    private ObjectMapper objectMapper; // Mocked ObjectMapper for JSON processing
    private CustomerReaderJSON reader; // Instance of the CustomerReaderJSON to be tested

    // Setup method to initialize the mocks and reader before each test
    @BeforeEach
    void setUp() {
        logger = Mockito.mock(Logger.class);
        objectMapper = Mockito.mock(ObjectMapper.class);
        reader = new CustomerReaderJSON(logger);
    }

    // Test for successfully reading valid customer data from a JSON file
    @Test
    void testReadValidCustomersFromJSON() throws IOException {
        filePath = "src/test/resources/JSONReader/customers.json"; // Set the file path for the test

        // Attempt to read customers from the JSON file
        List<Customer> customers = reader.readCustomers(filePath);

        // Assertions to verify that the list of customers is not null and contains the expected number of customers
        assertNotNull(customers, "The list of customers should not be null.");
        assertEquals(3, customers.size(), "The list should contain 3 customers.");

        // Assertions for each customer's properties to ensure they are read correctly
        assertEquals("John Doe", customers.get(0).getFullName(), "First customer's name should be John Doe.");
        assertEquals(20.00, customers.get(0).getTotalPurchase(), 0.01, "First customer's total purchase should be 20.00.");
        assertEquals(2, customers.get(0).getCustomerClass(), "First customer's class should be 2.");
        assertEquals(2023, customers.get(0).getLastPurchase(), "First customer's last purchase year should be 2023.");

        assertEquals("Kevin Mall", customers.get(1).getFullName(), "Second customer's name should be Kevin Mall.");
        assertEquals(30.00, customers.get(1).getTotalPurchase(), 0.01, "Second customer's total purchase should be 30.00.");
        assertEquals(3, customers.get(1).getCustomerClass(), "Second customer's class should be 3.");
        assertEquals(2024, customers.get(1).getLastPurchase(), "Second customer's last purchase year should be 2024.");

        assertEquals("Owen Will", customers.get(2).getFullName(), "Third customer's name should be Owen Will.");
        assertEquals(40.00, customers.get(2).getTotalPurchase(), 0.01, "Third customer's total purchase should be 40.00.");
        assertEquals(1, customers.get(2).getCustomerClass(), "Third customer's class should be 1.");
        assertEquals(2024, customers.get(2).getLastPurchase(), "Third customer's last purchase year should be 2024.");
    }

    // Test for handling invalid JSON input
    @Test
    void testReadInvalidJson() {
        // Given: a JSON file path with malformed data
        filePath = "src/test/resources/JSONReader/invalid_customers.json";

        // When: attempting to read customers from the invalid JSON file
        Exception exception = assertThrows(IOException.class, () -> {
            reader.readCustomers(filePath);
        });

        // Then: assert that a mapping or parsing exception is thrown
        assertTrue(exception.getMessage().contains("Malformed JSON in file") ||
                exception.getMessage().contains("Mapping error in JSON file"));
    }
}
