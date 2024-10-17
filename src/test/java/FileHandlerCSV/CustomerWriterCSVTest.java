package FileHandlerCSV;

import org.CCT.Entity.Customer;
import org.CCT.FileHandlerCSV.CustomerWriterCSV;
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

public class CustomerWriterCSVTest {

    private String filePath; // File path for the test CSV files

    private CustomerWriterCSV writer; // Instance of the writer to be tested
    private Logger logger; // Logger instance for mocking

    @BeforeEach
    public void setUp() {
        logger = Mockito.mock(Logger.class); // Create a mock logger
        writer = new CustomerWriterCSV(logger); // Initialize the writer with the mock logger
    }

    @Test
    void testWriteValidCustomers() throws IOException {
        // Specify the path to the test CSV file for writing
        filePath = "src/test/resources/CSVWriter/TestCustomers.csv";
        List<Customer> customers = Arrays.asList(
                new Customer("John Doe", 121.22, 1, 2022),
                new Customer("Kevin Murray", 1243.98, 2, 2023),
                new Customer("Max Maxwell", 560.00, 1, 2018)
        );

        // Write customers to the CSV file
        writer.writeCustomers(customers, filePath);

        // Read the lines from the written CSV file
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Check the number of lines written matches the number of customers
        assertEquals(customers.size(), lines.size(), "Number of lines written does not match number of customers");

        // Check each line against the expected format
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            String expectedLine = String.format("%s,%.2f", customer.getFullName(), customer.getDiscountedValue());
            assertEquals(expectedLine, lines.get(i), "Line " + (i + 1) + " format is incorrect");
        }
    }

    @Test
    void testWriteFromEmptyList() {
        // Specify the path to the test CSV file for writing
        filePath = "src/test/resources/CSVWriter/TestCustomers.csv";
        List<Customer> customers = new ArrayList<>(); // Create an empty list of customers

        // Ensure IOException is thrown for an empty customer list
        Exception exception = assertThrows(IOException.class, () -> {
            writer.writeCustomers(customers, filePath);
        });
        // Check the exception message
        assertTrue(exception.getMessage().contains("Cannot write empty customer list to file"));
    }
}
