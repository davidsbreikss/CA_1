package org.CCT.Loggers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class Logger {
    // Directory name for log files
    private static final String LOG_FOLDER = "logs";

    // Map to track log file names for each log level
    private final Map<LogLevel, String> logFileMap;

    // Initiates Logger instance which creates the log folder and initializes log file map
    public Logger() {
        createLogFolder(); // Create the log folder if it doesn't exist
        logFileMap = initializeLogFileMap(); // Initialize the mapping of log levels to file names
        // Check if the log file map was successfully initialized
        if (logFileMap.isEmpty()) {
            throw new IllegalStateException("logFileMap failed to initialize"); // Throw exception if initialization failed
        }
    }

    // Enum to define different log levels
    public enum LogLevel {
        INFO, ERROR, DEBUG, WARN // Various log levels available
    }

    // Create a directory for log files
    private void createLogFolder() {
        File folder = new File(LOG_FOLDER); // Create a File object for the log folder
        if (!folder.exists()) { // Check if the folder already exists
            // Attempt to create the log folder
            if (folder.mkdir()) {
                System.out.println("Log folder created."); // Notify if the folder is created successfully
            } else {
                System.err.println("Failed to create log folder."); // Notify if folder creation fails
                throw new IllegalStateException("Could not create log folder."); // Throw exception on failure
            }
        }
    }

    // Initialize file names for each log level
    private Map<LogLevel, String> initializeLogFileMap() {
        Map<LogLevel, String> map = new EnumMap<>(LogLevel.class); // Create a map to hold log level to file name mapping
        map.put(LogLevel.INFO, "info.log"); // Map INFO level to "info.log"
        map.put(LogLevel.ERROR, "error.log"); // Map ERROR level to "error.log"
        map.put(LogLevel.DEBUG, "debug.log"); // Map DEBUG level to "debug.log"
        map.put(LogLevel.WARN, "warn.log"); // Map WARN level to "warn.log"

        // Print the initialized map to console for debugging purposes
        System.out.println("logFileMap initialized with: " + map);
        return map; // Return the initialized map
    }

    // Log messages based on the class name and log level
    public synchronized void log(String className, LogLevel level, String message) {
        String methodName = "method name: " + Thread.currentThread().getStackTrace()[2].getMethodName(); // Get the name of the calling method
        String formattedMessage = formatLogMessage(className, level, methodName, message); // Format the log message

        // Write log to the corresponding log file
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(LOG_FOLDER, logFileMap.get(level)), true))) {
            writer.println(formattedMessage); // Write the formatted message to the log file
        } catch (IOException e) {
            // Fallback to console logging if writing to file fails
            logToConsole(level, "Failed to write log: " + e.getMessage()); // Pass the original level for console log
        }
    }

    // Format the log message
    private String formatLogMessage(String className, LogLevel level, String methodName, String message) {
        // Format the log message with timestamp, log level, class name, method name, and the actual message
        return String.format("%s [%s] [%s.%s]: %s", LocalDateTime.now(), level, className, methodName, message);
    }

    // Fallback logging to console (used when log writing fails)
    private void logToConsole(LogLevel level, String message) {
        // Print the message to standard error with a timestamp
        System.err.printf("%s [%s]: %s%n", LocalDateTime.now(), level, message);
    }
}
