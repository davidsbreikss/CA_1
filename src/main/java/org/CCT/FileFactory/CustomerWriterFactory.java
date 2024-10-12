package org.CCT.FileFactory;

import org.CCT.Entity.Customer;
import org.CCT.FileHandlerCSV.CustomerWriterCSV;
import org.CCT.FileHandlerInterface.CustomerWriter;
import org.CCT.FileHandlerJSON.CustomerWriterJSON;
import org.CCT.FileHandlerTxt.CustomerWriterTxt;
import org.CCT.Loggers.Logger;
import org.CCT.Main;

import java.io.IOException;
import java.util.List;

public class CustomerWriterFactory {
    private final Logger logger;

    public CustomerWriterFactory(Logger logger) {
        this.logger = logger;
    }

    // Method to get the appropriate CustomerWriter based on file extension
    public CustomerWriter getWriter(String filePath) {
        String fileExtension = getFileExtension(filePath);
        return switch (fileExtension.toLowerCase()) {
            case "txt" -> new CustomerWriterTxt();
            case "csv" -> new CustomerWriterCSV();
            case "json" -> new CustomerWriterJSON();
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