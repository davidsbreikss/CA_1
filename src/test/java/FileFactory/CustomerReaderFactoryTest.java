package FileFactory;

import org.CCT.FileFactory.CustomerReaderFactory;
import org.CCT.FileHandlerCSV.CustomerReaderCSV;
import org.CCT.FileHandlerInterface.CustomerReader;
import org.CCT.FileHandlerJSON.CustomerReaderJSON;
import org.CCT.FileHandlerTxt.CustomerReaderTxt;
import org.CCT.Loggers.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;

public class CustomerReaderFactoryTest {

    private Logger logger; // Logger instance for mocking
    private CustomerReaderFactory factory; // Factory instance to be tested

    @BeforeEach
    void setUp() {
        logger = Mockito.mock(Logger.class); // Create a mock logger
        factory = new CustomerReaderFactory(logger); // Initialize the factory with the mock logger
    }

    @Test
    void testGetReaderWithTxtFile() {
        // Act
        CustomerReader reader = factory.getReader("test.txt");
        // Assert that the reader is an instance of CustomerReaderTxt
        assertInstanceOf(CustomerReaderTxt.class, reader);
    }

    @Test
    void testGetReaderWithCsvFile() {
        // Act
        CustomerReader reader = factory.getReader("test.csv");
        // Assert that the reader is an instance of CustomerReaderCSV
        assertInstanceOf(CustomerReaderCSV.class, reader);
    }

    @Test
    void testGetReaderWithJsonFile() {
        // Act
        CustomerReader reader = factory.getReader("test.json");
        // Assert that the reader is an instance of CustomerReaderJSON
        assertInstanceOf(CustomerReaderJSON.class, reader);
    }

    @Test
    void testGetReaderWithInvalidExtension() {
        // Ensure an exception is thrown for unsupported file extensions
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getReader("test.invalid");
        });
        // Check the exception message
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetReaderWithNoExtension() {
        // Ensure an exception is thrown when no file extension is provided
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getReader("file");
        });
        // Check the exception message
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetReaderWithEmptyFileName() {
        // Ensure an exception is thrown for empty file names
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getReader("");
        });
        // Check the exception message
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetReaderWithUnsupportedFileType() {
        // Ensure an exception is thrown for unsupported file types
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getReader("test.pdf");
        });
        // Check the exception message
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }
}
