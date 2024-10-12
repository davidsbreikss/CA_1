package org.CCT;

import org.CCT.Entity.Customer;
import org.CCT.FileFactory.CustomerReaderFactory;
import org.CCT.FileFactory.CustomerWriterFactory;
import org.CCT.FileHandlerInterface.CustomerReader;
import org.CCT.FileHandlerInterface.CustomerWriter;
import org.CCT.Loggers.Logger;
import org.CCT.Processor.CustomerProcessor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String inputFilePath = "customers.txt";
        String outputDirectory = "output";

        Logger logger = new Logger();
        CustomerReaderFactory readerFactory = new CustomerReaderFactory(logger);
        CustomerWriterFactory writerFactory = new CustomerWriterFactory(logger);
        CustomerProcessor customerProcessor = new CustomerProcessor(logger);

        try {
            // Ensure the output directory exists
            Path outputPath = Paths.get(outputDirectory);
            Files.createDirectories(outputPath); // Create the output directory if it doesn't exist

            // Get the appropriate reader based on the file type
            CustomerReader customerReader = readerFactory.getReader(inputFilePath);
            List<Customer> customers = customerReader.readCustomers(inputFilePath);

            // Process the customers
            List<Customer> processedCustomers = customerProcessor.processCustomers(customers);

            // Determine the output file paths for different formats
            String outputTxtFilePath = getOutputFilePath(outputDirectory, "txt");
            String outputCsvFilePath = getOutputFilePath(outputDirectory, "csv");
            String outputJsonFilePath = getOutputFilePath(outputDirectory, "json");

            // Ensure the output files exist
            ensureOutputFileExists(outputTxtFilePath);
            ensureOutputFileExists(outputCsvFilePath);
            ensureOutputFileExists(outputJsonFilePath);

            // Generate the output files based on the desired formats
            generateOutputFile(processedCustomers, outputTxtFilePath, writerFactory, logger);
            generateOutputFile(processedCustomers, outputCsvFilePath, writerFactory, logger);
            generateOutputFile(processedCustomers, outputJsonFilePath, writerFactory, logger);

        } catch (IOException e) {
            logger.log(Main.class.getSimpleName(), Logger.LogLevel.ERROR, "Error processing customer data: " + e.getMessage());
        }
    }

    private static String getOutputFilePath(String outputDirectory, String fileType) {
        return outputDirectory + "/customerDiscount." + fileType; // Constructs the file path based on the type
    }

    private static void ensureOutputFileExists(String outputFilePath) throws IOException {
        Path path = Paths.get(outputFilePath);
        if (!Files.exists(path)) {
            Files.createFile(path); // Creates the file if it doesn't exist
        }
    }
    private static void generateOutputFile(List<Customer> processedCustomers, String outputFilePath,
                                           CustomerWriterFactory writerFactory, Logger logger) {
        try {
            // Get the appropriate writer based on the output file type
            CustomerWriter customerWriter = writerFactory.getWriter(outputFilePath);
            customerWriter.writeCustomers(processedCustomers, outputFilePath);
            logger.log(Main.class.getSimpleName(), Logger.LogLevel.INFO, "Processed customers written to: " + outputFilePath);
        } catch (IOException e) {
            logger.log(Main.class.getSimpleName(), Logger.LogLevel.ERROR, "Error writing customer data: " + e.getMessage());
        }
    }
}