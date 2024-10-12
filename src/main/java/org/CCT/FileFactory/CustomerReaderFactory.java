package org.CCT.FileFactory;

import org.CCT.FileHandlerCSV.CustomerReaderCSV;
import org.CCT.FileHandlerInterface.CustomerReader;
import org.CCT.FileHandlerJSON.CustomerReaderJSON;
import org.CCT.FileHandlerTxt.CustomerReaderTxt;
import org.CCT.Loggers.Logger;

public class CustomerReaderFactory {
    private final Logger logger;

    public CustomerReaderFactory(Logger logger) {
        this.logger = logger;
    }

    // Method to get the appropriate CustomerReader based on file extension
    public CustomerReader getReader(String filePath) {
        String fileExtension = getFileExtension(filePath);
        return switch (fileExtension.toLowerCase()) {
            case "txt" -> new CustomerReaderTxt(logger);
            case "csv" -> new CustomerReaderCSV(logger);
            case "json" -> new CustomerReaderJSON(logger);
            default -> throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        };
    }

    // Helper method to extract the file extension
    private String getFileExtension(String filePath) {
        int lastIndexOfDot = filePath.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return ""; // No extension
        }
        return filePath.substring(lastIndexOfDot + 1);
    }
}
