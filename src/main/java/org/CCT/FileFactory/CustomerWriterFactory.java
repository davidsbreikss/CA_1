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
        FileType fileType = FileType.fromExtension(getFileExtension(filePath));
        return switch (fileType) {
            case TXT -> new CustomerWriterTxt(logger);
            case CSV -> new CustomerWriterCSV(logger);
            case JSON -> new CustomerWriterJSON(logger);
        };
    }

    // Helper method to extract the file extension
    private String getFileExtension(String filePath) {
        int lastIndexOfDot = filePath.lastIndexOf('.');
        if (lastIndexOfDot == -1 || lastIndexOfDot == filePath.length() - 1) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "No valid file extension found for file: " + filePath);
            throw new IllegalArgumentException("Writing File must have a valid extension: " + filePath);
        }
        return filePath.substring(lastIndexOfDot + 1);
    }
}