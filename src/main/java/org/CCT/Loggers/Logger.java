package org.CCT.Loggers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class Logger {
    // Initiate log folder
    private static final String LOG_FOLDER = "logs";

    // Map to track log file names for each log level
    private final Map<LogLevel, String> logFileMap;

    // Initiate Logger instance which has createLogFolder method
    public Logger() {
        createLogFolder();
        logFileMap = initializeLogFileMap();
    }

    public enum LogLevel {
        INFO, ERROR, DEBUG, WARN
    }

    // Create a directory for log files
    private void createLogFolder() {
        File folder = new File(LOG_FOLDER);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Log folder created.");
            } else {
                System.err.println("Failed to create log folder.");
            }
        }
    }

    // Initialize file names for each log level
    private Map<LogLevel, String> initializeLogFileMap() {
        Map<LogLevel, String> map = new EnumMap<>(LogLevel.class);
        map.put(LogLevel.INFO, "info.log");
        map.put(LogLevel.ERROR, "error.log");
        map.put(LogLevel.DEBUG, "debug.log");
        map.put(LogLevel.WARN, "warn.log");
        return map;
    }

    // Log messages based on the class name
    public synchronized void log(String className, LogLevel level, String message) {
        String methodName = "method name: " + Thread.currentThread().getStackTrace()[2].getMethodName();
        String formattedMessage = formatLogMessage(className, level, methodName, message);

        // Write log to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(LOG_FOLDER, logFileMap.get(level)), true))) {
            writer.println(formattedMessage);
        } catch (IOException e) {
            logToConsole(level, "Failed to write log: " + e.getMessage()); // Pass the original level
        }
    }

    // Format the log message
    private String formatLogMessage(String className, LogLevel level, String methodName, String message) {
        return String.format("%s [%s] [%s.%s]: %s", LocalDateTime.now(), level, className, methodName, message);
    }

    // Fallback logging to console (used when log writing fails)
    private void logToConsole(LogLevel level, String message) {
        System.err.printf("%s [%s]: %s%n", LocalDateTime.now(), level, message);
    }
}