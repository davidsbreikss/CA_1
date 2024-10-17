package FileHandlerCSV;

import org.CCT.Entity.Customer;
import org.CCT.Exceptions.EmptyFileException;
import org.CCT.FileHandlerCSV.CustomerReaderCSV;
import org.CCT.Loggers.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerReaderCSVTest {

    private String filePath; // File path for the test CSV files

    private Logger logger; // Logger instance for mocking
    private CustomerReaderCSV reader; // Instance of the reader to be tested

    @BeforeEach
    public void setUp() {
        logger = Mockito.mock(Logger.class); // Create a mock logger
        reader = new CustomerReaderCSV(logger); // Initialize the reader with the mock logger
    }

    @Test
    void testReadCustomersSuccessfully() throws IOException, EmptyFileException {
        // Specify the path to the test CSV file with valid customers
        filePath = "src/test/resources/CSVReader/TestCustomers.csv";

        // Act: Read customers from the CSV file
        List<Customer> customers = reader.readCustomers(filePath);

        // Verify correct customer data
        assertEquals(2, customers.size()); // Check the number of customers read

        // Verify details of the first customer
        assertEquals("John Doe", customers.get(0).getFullName());
        assertEquals(100.0, customers.get(0).getTotalPurchase());
        assertEquals(1, customers.get(0).getCustomerClass());
        assertEquals(2022, customers.get(0).getLastPurchase());

        // Verify details of the second customer
        assertEquals("Jane Doe", customers.get(1).getFullName());
        assertEquals(200.0, customers.get(1).getTotalPurchase());
        assertEquals(2, customers.get(1).getCustomerClass());
        assertEquals(2023, customers.get(1).getLastPurchase());
    }

    @Test
    void testReadCustomersEmptyFile() {
        // Specify the path to the test CSV file that is empty
        filePath = "src/test/resources/CSVReader/TestEmptyCustomers.csv";

        // Ensure an EmptyFileException is thrown for an empty file
        Exception exception = assertThrows(EmptyFileException.class, () -> {
            reader.readCustomers(filePath);
        });
        // Check the exception message
        assertTrue(exception.getMessage().contains("File is empty"));
    }

    @Test
    void testReadCustomersWithMissingFields() throws EmptyFileException, IOException {
        // Specify the path to the test CSV file with missing fields
        filePath = "src/test/resources/CSVReader/TestMissingFieldCustomers.csv";

        // Act: Read customers from the file
        List<Customer> customers = reader.readCustomers(filePath);
        // Verify that the method skips customers with missing fields
        assertEquals(1, customers.size()); // Only valid customers should be present
    }

    @Test
    void testReadCustomersWithInvalidNumberFormat() throws IOException, EmptyFileException {
        // Specify the path to the test CSV file with invalid number formats
        filePath = "src/test/resources/CSVReader/TestInvalidNumberCustomers.csv";

        // Read customers from the file
        List<Customer> customers = reader.readCustomers(filePath);
        // Verify that the method skips customers with invalid number formats
        assertEquals(1, customers.size()); // Only valid customers should be present
    }

    @Test
    void testCustomersWithEmptyFields() throws EmptyFileException, IOException {
        // Specify the path to the test CSV file with empty fields
        filePath = "src/test/resources/CSVReader/TestEmptyFields";

        // Read customers from the file
        List<Customer> customers = reader.readCustomers(filePath);
        // Verify that the method skips customers with empty fields
        assertEquals(1, customers.size()); // Only valid customers should be present
    }

    @Test
    void testReadCustomersNonExistentFile() {
        // Ensure IOException is thrown for a non-existent file
        assertThrows(IOException.class, () -> reader.readCustomers("non_existent_file.csv"));
    }
}
