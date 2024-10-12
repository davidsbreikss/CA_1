package org.CCT.FileFactory;

import org.CCT.EnumFiles.FileType;
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
        FileType fileType = FileType.fromExtension(getFileExtension(filePath));
        return switch (fileType) {
            case TXT -> new CustomerReaderTxt(logger);
            case CSV -> new CustomerReaderCSV(logger);
            case JSON -> new CustomerReaderJSON(logger);
        };
    }

    // Helper method to extract the file extension
    private String getFileExtension(String filePath) {
        int lastIndexOfDot = filePath.lastIndexOf('.');
        if (lastIndexOfDot == -1 || lastIndexOfDot == filePath.length() - 1) {
            logger.log(this.getClass().getSimpleName(), Logger.LogLevel.ERROR, "No valid file extension found for file: " + filePath);
            throw new IllegalArgumentException("File must have a valid extension: " + filePath);
        }
        return filePath.substring(lastIndexOfDot + 1);
    }
}