import org.CCT.Entity.Customer;
import org.CCT.Loggers.Logger;
import org.CCT.Processor.CustomerProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CustomerProcessorTest {

    private CustomerProcessor processor; // Instance of the CustomerProcessor to be tested
    private Logger logger; // Logger instance for logging

    // Setup method to initialize the processor and logger before each test
    @BeforeEach
    void setUp() {
        logger = new Logger(); // Create a new Logger instance
        processor = new CustomerProcessor(logger); // Initialize the processor with the logger
    }

    // Test for processing valid customers and applying discounts
    @Test
    void testProcessCustomers() {
        // Given: A list of valid customers
        List<Customer> customers = Arrays.asList(
                new Customer("John Doe", 100.00, 1, 2024),
                new Customer("Kevin Murray", 200.00, 2, 2023),
                new Customer("Max Maxwell", 300.00, 3, 2018)
        );

        // When: Processing the customers
        List<Customer> processedCustomers = processor.processCustomers(customers);

        // Assert that all customers are processed (none are filtered out)
        assertEquals(3, processedCustomers.size());

        // Check discounts applied to each customer
        assertEquals(70.00, processedCustomers.get(0).getDiscountedValue(), 0.01); // Expected discounted value for John Doe
        assertEquals(174.00, processedCustomers.get(1).getDiscountedValue(), 0.01); // Expected discounted value for Kevin Murray
        assertEquals(300.00, processedCustomers.get(2).getDiscountedValue(), 0.01); // Expected discounted value for Max Maxwell
    }

    // Test for handling invalid customers and ensuring they are filtered out
    @Test
    void testProcessInvalidCustomers() {
        // A mocked Logger instance
        Logger mockLogger = mock(Logger.class);
        CustomerProcessor processor = new CustomerProcessor(mockLogger); // Initialize the processor with the mocked logger

        // Creating invalid customers to trigger different validation methods
        List<Customer> customers = Arrays.asList(
                new Customer("John", 100.00, 1, 2024), // Invalid full name (only one part, missing last name)
                new Customer("Jane123 Doe", 100.00, 1, 2022), // Invalid first name (contains numbers)
                new Customer("John Doe", -50.00, 1, 2023) // Invalid total purchase (negative value)
        );

        // Processing the invalid customers
        List<Customer> processedCustomers = processor.processCustomers(customers);

        // Assert that no valid customers were processed (all were filtered out)
        assertEquals(0, processedCustomers.size());

        // Verify that logger logged 3 error messages, one for each invalid customer
        verify(mockLogger, times(3)).log(anyString(), eq(Logger.LogLevel.ERROR), anyString());
    }

    // Test for verifying that logging occurs during processing
    @Test
    void testLogging() {
        // A mocked Logger instance
        Logger mockLogger = mock(Logger.class);
        CustomerProcessor processor = new CustomerProcessor(mockLogger); // Initialize the processor with the mocked logger

        // Creating a valid customer
        List<Customer> customers = List.of(new Customer("John Doe", 100.00, 1, 2024));

        // Processing the valid customer
        processor.processCustomers(customers);

        // Verify that log messages were generated
        verify(mockLogger).log(eq(CustomerProcessor.class.getSimpleName()), eq(Logger.LogLevel.INFO), contains("Customers in input file before processing: 1"));
        verify(mockLogger).log(eq(CustomerProcessor.class.getSimpleName()), eq(Logger.LogLevel.INFO), contains("Customers in output file after processing: 1"));
    }
}
