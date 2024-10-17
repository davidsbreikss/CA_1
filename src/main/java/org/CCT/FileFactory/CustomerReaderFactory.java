package org.CCT.FileFactory;

import org.CCT.EnumFiles.FileType;
import org.CCT.FileHandlerCSV.CustomerReaderCSV;
import org.CCT.FileHandlerInterface.CustomerReader;
import org.CCT.FileHandlerJSON.CustomerReaderJSON;
import org.CCT.FileHandlerTxt.CustomerReaderTxt;
import org.CCT.Loggers.Logger;

public class CustomerReaderFactory {
    private final Logger logger;

    // Constructor that initializes the factory with a Logger instance
    public CustomerReaderFactory(Logger logger) {
        this.logger = logger;
    }

    // Method to get the appropriate CustomerReader based on file extension
    public CustomerReader getReader(String filePath) {
        String extension = getFileExtension(filePath); // Extract file extension
        try {
            FileType fileType = FileType.fromExtension(extension); // Convert extension to FileType
            return switch (fileType) {
                case TXT -> new CustomerReaderTxt(logger); // Return TXT reader
                case CSV -> new CustomerReaderCSV(logger); // Return CSV reader
                case JSON -> new CustomerReaderJSON(logger); // Return JSON reader
            };
        } catch (IllegalArgumentException e) {
            // Log error if the file type is unsupported
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Unsupported file: " + e.getMessage());
            throw new IllegalArgumentException("File must have a valid extension (txt, json or csv)");
        }
    }

    // Helper method to extract the file extension
    private String getFileExtension(String filePath) {
        int lastIndexOfDot = filePath.lastIndexOf('.'); // Find the last dot in the file path
        if (lastIndexOfDot == -1 || lastIndexOfDot == filePath.length() - 1) {
            // Log error if no valid file extension is found
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "No valid file extension found for file: " + filePath);
            throw new IllegalArgumentException("File must have a valid extension (txt, json or csv)");
        }
        return filePath.substring(lastIndexOfDot + 1); // Return the substring after the last dot
    }
}
