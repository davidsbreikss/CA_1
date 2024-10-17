package FileFactory;

import org.CCT.FileFactory.CustomerWriterFactory;
import org.CCT.FileHandlerInterface.CustomerWriter;
import org.CCT.Loggers.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerWriterFactoryTest {

    private Logger logger; // Logger instance for mocking
    private CustomerWriterFactory factory; // Factory instance to be tested

    @BeforeEach
    void setUp() {
        logger = Mockito.mock(Logger.class); // Create a mock logger
        factory = new CustomerWriterFactory(logger); // Initialize the factory with the mock logger
    }

    @Test
    void testGetWriterWithTxtFile() {
        // Act
        CustomerWriter writer = factory.getWriter("test.txt");
        // Assert that the writer is an instance of CustomerWriter
        assertInstanceOf(CustomerWriter.class, writer);
    }

    @Test
    void testGetWriterWithCsvFile() {
        // Act
        CustomerWriter writer = factory.getWriter("test.csv");
        // Assert that the writer is an instance of CustomerWriter
        assertInstanceOf(CustomerWriter.class, writer);
    }

    @Test
    void testGetWriterWithJsonFile() {
        // Act
        CustomerWriter writer = factory.getWriter("test.json");
        // Assert that the writer is an instance of CustomerWriter
        assertInstanceOf(CustomerWriter.class, writer);
    }

    @Test
    void testGetWriterWithInvalidExtension() {
        // Act and Assert: Ensure an exception is thrown for unsupported file extensions
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getWriter("test.invalid");
        });
        // Check the exception message
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetWriterWithNoExtension() {
        // Act and Assert: Ensure an exception is thrown when no file extension is provided
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getWriter("file");
        });
        // Check the exception message
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetWriterWithEmptyFileName() {
        // Act and Assert: Ensure an exception is thrown for empty file names
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getWriter("");
        });
        // Check the exception message
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetWriterWithUnsupportedFileType() {
        // Act and Assert: Ensure an exception is thrown for unsupported file types
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getWriter("test.pdf");
        });
        // Check the exception message
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }
}
