package org.CCT;

import org.CCT.Entity.Customer;
import org.CCT.Exceptions.CustomerDataException;
import org.CCT.Exceptions.EmptyFileException;
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

        String inputFilePath = "src/main/resources/customers.json"; // Path to the input customer data file
        String outputDirectory = "output"; // Directory where output files will be stored

        // Initialize the logger, factories, and processor
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
            List<Customer> customers = customerReader.readCustomers(inputFilePath); // Read customers from the input file

            // Process the customers to apply discounts
            List<Customer> processedCustomers = customerProcessor.processCustomers(customers);

            // Determine the output file paths for different formats (txt, csv, json)
            String outputTxtFilePath = getOutputFilePath(outputDirectory, "txt");
            String outputCsvFilePath = getOutputFilePath(outputDirectory, "csv");
            String outputJsonFilePath = getOutputFilePath(outputDirectory, "json");

            // Ensure the output files exist (create them if they don't)
            ensureOutputFileExists(outputTxtFilePath);
            ensureOutputFileExists(outputCsvFilePath);
            ensureOutputFileExists(outputJsonFilePath);

            // Generate the output files based on the processed customer data
            generateOutputFile(processedCustomers, outputTxtFilePath, writerFactory, logger);
            generateOutputFile(processedCustomers, outputCsvFilePath, writerFactory, logger);
            generateOutputFile(processedCustomers, outputJsonFilePath, writerFactory, logger);

        } catch (IOException e) {
            // Log an error message if there's an issue with file processing
            logger.log(Main.class.getSimpleName(), Logger.LogLevel.ERROR, "Error processing customer data: " + e.getMessage());
        } catch (CustomerDataException e) {
            // Handle custom exception for customer data issues
            throw new RuntimeException(e);
        } catch (EmptyFileException e) {
            // Handle custom exception for empty file issues
            throw new RuntimeException(e);
        }
    }

    // Method to construct the output file path based on the desired file type
    private static String getOutputFilePath(String outputDirectory, String fileType) {
        return outputDirectory + "/customerDiscount." + fileType; // Constructs the file path based on the type
    }

    // Method to ensure the output file exists, creating it if necessary
    private static void ensureOutputFileExists(String outputFilePath) throws IOException {
        Path path = Paths.get(outputFilePath);
        if (!Files.exists(path)) {
            Files.createFile(path); // Creates the file if it doesn't exist
        }
    }

    // Method to generate the output file for the processed customer data
    private static void generateOutputFile(List<Customer> processedCustomers, String outputFilePath,
                                           CustomerWriterFactory writerFactory, Logger logger) {
        try {
            // Get the appropriate writer based on the output file type
            CustomerWriter customerWriter = writerFactory.getWriter(outputFilePath);
            customerWriter.writeCustomers(processedCustomers, outputFilePath); // Write the processed customers to the output file
            logger.log(Main.class.getSimpleName(), Logger.LogLevel.INFO, "Processed customers written to: " + outputFilePath);
        } catch (IOException e) {
            // Log an error message if there's an issue with writing data
            logger.log(Main.class.getSimpleName(), Logger.LogLevel.ERROR, "Error writing customer data: " + e.getMessage());
        }
    }
}