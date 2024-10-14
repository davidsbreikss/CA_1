package FileFactory;

import org.CCT.FileFactory.CustomerWriterFactory;
import org.CCT.FileHandlerInterface.CustomerWriter;
import org.CCT.Loggers.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerWriterFactoryTest {

    private Logger logger;
    private CustomerWriterFactory factory;

    @BeforeEach
    void setUp() {
        logger = Mockito.mock(Logger.class);
        factory = new CustomerWriterFactory(logger);
    }

    @Test
    void testGetWriterWithTxtFile() {
        CustomerWriter reader = factory.getWriter("test.txt");
        assertInstanceOf(CustomerWriter.class, reader);
    }

    @Test
    void testGetWriterWithCsvFile() {
        CustomerWriter reader = factory.getWriter("test.csv");
        assertInstanceOf(CustomerWriter.class, reader);
    }

    @Test
    void testGetWriterWithJsonFile() {
        CustomerWriter reader = factory.getWriter("test.json");
        assertInstanceOf(CustomerWriter.class, reader);
    }

    @Test
    void testGetWriterWithInvalidExtension() {
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getWriter("test.invalid");
        });
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetWriterWithNoExtension() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getWriter("file");
        });
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetWriterWithEmptyFileName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getWriter("");
        });
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }

    @Test
    void testGetWriterWithUnsupportedFileType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.getWriter("test.pdf");
        });
        assertEquals("File must have a valid extension (txt, json or csv)", exception.getMessage());
    }
}