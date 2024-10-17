package FileHandlerTXT;

import org.CCT.Entity.Customer;
import org.CCT.Exceptions.EmptyFileException;
import org.CCT.FileHandlerTxt.CustomerReaderTxt;
import org.CCT.Loggers.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerReaderTxtTest {

    private String filePath; // Variable to hold the file path for testing

    private Logger logger; // Mocked Logger instance for logging
    private CustomerReaderTxt reader; // Instance of the CustomerReaderTxt to be tested

    // Setup method to initialize the mocks and reader before each test
    @BeforeEach
    void setUp() {
        logger = Mockito.mock(Logger.class);
        reader = new CustomerReaderTxt(logger);
    }

    // Test for successfully reading valid customer data from a text file
    @Test
    void testReadValidCustomers() throws IOException, EmptyFileException {
        // Given: A valid customers file
        filePath = "src/test/resources/TXTReader/valid_customers.txt"; // Adjust the path as needed

        // When: Reading customers from the file
        List<Customer> customers = reader.readCustomers(filePath);

        // Then: Verify the content of the customer list
        assertNotNull(customers, "Customer list should not be null.");
        assertEquals(3, customers.size(), "We expect 3 valid customers."); // We expect 3 valid customers

        // Verify specific customer attributes for the first customer
        assertEquals("Jack Black", customers.get(0).getFullName(), "First customer's name is incorrect.");
        assertEquals(523.00, customers.get(0).getTotalPurchase(), "First customer's total purchase is incorrect.");
        assertEquals(2, customers.get(0).getCustomerClass(), "First customer's class is incorrect.");
        assertEquals(2023, customers.get(0).getLastPurchase(), "First customer's last purchase year is incorrect.");

        // Verify specific customer attributes for the second customer
        assertEquals("Kyle Moran", customers.get(1).getFullName(), "Second customer's name is incorrect.");
        assertEquals(1523.00, customers.get(1).getTotalPurchase(), "Second customer's total purchase is incorrect.");
        assertEquals(3, customers.get(1).getCustomerClass(), "Second customer's class is incorrect.");
        assertEquals(2024, customers.get(1).getLastPurchase(), "Second customer's last purchase year is incorrect.");

        // Verify specific customer attributes for the third customer
        assertEquals("Bob Walsh", customers.get(2).getFullName(), "Third customer's name is incorrect.");
        assertEquals(271.00, customers.get(2).getTotalPurchase(), "Third customer's total purchase is incorrect.");
        assertEquals(1, customers.get(2).getCustomerClass(), "Third customer's class is incorrect.");
        assertEquals(2024, customers.get(2).getLastPurchase(), "Third customer's last purchase year is incorrect.");
    }

    // Test for handling empty customer file
    @Test
    void testReadEmptyFile() throws IOException, EmptyFileException {
        // Given: An empty customers file
        filePath = "src/test/resources/TXTReader/customers_empty_file.txt";

        // When: Attempting to read customers from the empty file, an exception is expected
        Exception exception = assertThrows(EmptyFileException.class, () -> {
            reader.readCustomers(filePath);
        });

        // Then: Assert that the exception message indicates the file is empty
        assertTrue(exception.getMessage().contains("The file is empty"), "Expected exception message not found.");
    }
}
