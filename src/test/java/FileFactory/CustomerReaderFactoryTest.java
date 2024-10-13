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

    private Logger logger;
    private CustomerReaderFactory factory;

    @BeforeEach
    void setUp() {
        logger = Mockito.mock(Logger.class);
        factory = new CustomerReaderFactory(logger);
    }

    @Test
    void testGetReaderWithTxtFile() {
        CustomerReader reader = factory.getReader("test.txt");
        assertInstanceOf(CustomerReaderTxt.class, reader);
    }

    @Test
    void testGetReaderWithCsvFile() {
        CustomerReader reader = factory.getReader("test.csv");
        assertInstanceOf(CustomerReaderCSV.class, reader);
    }

    @Test
    void testGetReaderWithJsonFile() {
        CustomerReader reader = factory.getReader("test.json");
        assertInstanceOf(CustomerReaderJSON.class, reader);
    }

    @Test
    void testGetReaderWithInvalidExtension() {
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getReader("test.invalid");
        });
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetReaderWithNoExtension() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getReader("file");
        });
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetReaderWithEmptyFileName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getReader("");
        });
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetReaderWithUnsupportedFileType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getReader("test.pdf");
        });
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }
}