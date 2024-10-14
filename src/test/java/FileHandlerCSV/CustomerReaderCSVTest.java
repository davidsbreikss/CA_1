package FileHandlerCSV;

import org.CCT.Entity.Customer;
import org.CCT.Exceptions.CustomerDataException;
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

    private Logger logger;
    private CustomerReaderCSV reader;

    @BeforeEach
    public void setUp() {
        logger = Mockito.mock(Logger.class);
        reader = new CustomerReaderCSV(logger);
    }

    @Test
    void testReadCustomersSuccessfully() throws IOException, CustomerDataException, EmptyFileException {
        String testFilePath = "src/test/resources/TestCustomers.csv";

        // Read customers from the CSV file
        List<Customer> customers = reader.readCustomers(testFilePath);

        // Assertions to verify correct customer data
        assertEquals(2, customers.size());

        assertEquals("John Doe", customers.getFirst().getFullName());
        assertEquals(100.0, customers.getFirst().getTotalPurchase());
        assertEquals(1, customers.get(0).getCustomerClass());
        assertEquals(2022, customers.get(0).getLastPurchase());

        assertEquals("Jane Doe", customers.get(1).getFullName());
        assertEquals(200.0, customers.get(1).getTotalPurchase());
        assertEquals(2, customers.get(1).getCustomerClass());
        assertEquals(2023, customers.get(1).getLastPurchase());

    }

    @Test
    void testReadCustomersWithInvalidDataFormat() throws IOException, CustomerDataException, EmptyFileException {

        String testFilePath = "src/test/resources/TestInvalidDataCustomers.csv";

        List<Customer> customers = reader.readCustomers(testFilePath);

        assertEquals(1, customers.size());
    }

    @Test
    void testReadCustomersWithMultipleInvalidDataFields() throws IOException, CustomerDataException, EmptyFileException {

        String testFilePath = "src/test/resources/TestMultipleInvalidDataCustomers.csv";
        // Mock Logger
        Logger logger = Mockito.mock(Logger.class);

        // Create instance of CustomerReaderCSV
        CustomerReaderCSV reader = new CustomerReaderCSV(logger);

        // Read customers from file
        List<Customer> customers = reader.readCustomers(testFilePath);

        // Verify that only valid customers were added (assuming 1 valid customer in the file)
        assertEquals(1, customers.size());

        // Verify that the logger caught multiple invalid data formats
        Mockito.verify(logger, Mockito.times(1)).log(Mockito.anyString(), Mockito.eq(Logger.LogLevel.ERROR), Mockito.contains("Invalid data format in line"));
    }

    @Test
    void testReadCustomersWithMissingDataFields() {
        String testFilePath = "src/test/resources/TestMissingDataCustomers.csv";

        // Read customers from file and expect a CustomerDataException
        Exception exception = assertThrows(CustomerDataException.class, () -> {
            reader.readCustomers(testFilePath);
        });

        // Verify the exception message contains details about invalid customer data
        assertTrue(exception.getMessage().contains("Invalid customer data"));
    }

    @Test
    void testReadCustomersWithEmptyFile() {
        String testFilePath = "src/test/resources/TestEmptyCustomers.csv";

        Exception exception = assertThrows(EmptyFileException.class, () -> {
            reader.readCustomers(testFilePath);
        });

        // Verify that no customers were read
        assertTrue(exception.getMessage().contains("File is empty"));
    }
}