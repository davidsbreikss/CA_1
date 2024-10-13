package org.CCT.FileFactory;

import org.CCT.EnumFiles.FileType;
import org.CCT.FileHandlerCSV.CustomerWriterCSV;
import org.CCT.FileHandlerInterface.CustomerWriter;
import org.CCT.FileHandlerJSON.CustomerWriterJSON;
import org.CCT.FileHandlerTxt.CustomerWriterTxt;
import org.CCT.Loggers.Logger;

public class CustomerWriterFactory {
    private final Logger logger;

    public CustomerWriterFactory(Logger logger) {
        this.logger = logger;
    }

    // Method to get the appropriate CustomerWriter based on file extension
    public CustomerWriter getWriter(String filePath) {
        String extension = getFileExtension(filePath);
        try {
            FileType fileType = FileType.fromExtension(extension);
            return switch (fileType) {
                case TXT -> new CustomerWriterTxt(logger);
                case CSV -> new CustomerWriterCSV(logger);
                case JSON -> new CustomerWriterJSON(logger);
            };
        } catch (IllegalArgumentException e) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "Unsupported file: " + e.getMessage());
            throw new IllegalArgumentException("File must have a valid extension (txt, json or csv)");
        }
    }

    // Helper method to extract the file extension
    private String getFileExtension(String filePath) {
        int lastIndexOfDot = filePath.lastIndexOf('.');
        if (lastIndexOfDot == -1 || lastIndexOfDot == filePath.length() - 1) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "No valid file extension found for file: " + filePath);
            throw new IllegalArgumentException("File must have a valid extension (txt, json or csv)");
        }
        return filePath.substring(lastIndexOfDot + 1);
    }
}