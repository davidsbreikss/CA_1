package FileHandlerTXT;

import org.CCT.Entity.Customer;
import org.CCT.FileHandlerTxt.CustomerWriterTxt;
import org.CCT.Loggers.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerWriterTxtTest {

    private String filePath; // Variable to hold the file path for testing

    private CustomerWriterTxt writer; // Instance of the CustomerWriterTxt to be tested
    private Logger logger; // Mocked Logger instance for logging

    // Setup method to initialize the mocks and writer before each test
    @BeforeEach
    public void setUp() {
        logger = Mockito.mock(Logger.class);
        writer = new CustomerWriterTxt(logger);
    }

    // Test for successfully writing customer data to a text file
    @Test
    void testWriteCustomers() throws IOException {
        // Given: A valid list of customers and a file path
        filePath = "src/test/resources/TXTReader/customers.txt";
        List<Customer> customers = Arrays.asList(
                new Customer("John Doe", 121.22, 1, 2022),
                new Customer("Kevin Murray", 1243.98, 2, 2023),
                new Customer("Max Maxwell", 560.00, 1, 2018));

        // When: Writing the customers to the specified text file
        writer.writeCustomers(customers, filePath);

        // Read the lines from the written file for verification
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Verify that each customer's information is written correctly
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);

            // Expect the full name in the first line
            assertEquals(customer.getFullName(), lines.get(i * 2), "Customer full name is incorrect at line " + (i * 2 + 1));

            // Ensure that the second line contains a valid numeric value (e.g., a decimal number)
            String discountedValueLine = lines.get(i * 2 + 1);
            assertTrue(discountedValueLine.matches("\\d+\\.\\d{2}"), "Discounted value is not a valid decimal number at line " + (i * 2 + 2));
        }
    }

    // Test for handling attempts to write an empty list of customers
    @Test
    void testEmptyList() {
        // Given: An empty list of customers
        filePath = "src/test/resources/TXTReader/customers.txt";
        List<Customer> customers = new ArrayList<>();

        // When: Attempting to write the empty customer list, an exception is expected
        Exception exception = assertThrows(IOException.class, () -> {
            writer.writeCustomers(customers, filePath);
        });

        // Then: Assert that the exception message indicates the list is empty
        assertTrue(exception.getMessage().contains("Cannot write empty customer list to file"), "Expected exception message not found.");
    }
}
