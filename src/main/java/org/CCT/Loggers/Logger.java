package org.CCT.Loggers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Logger {

    private static final String LOG_FOLDER = "logs";

    public Logger() {
        createLogFolder();
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

    // Log messages based on the class name
    public void log(String className, String level, String message) {
        // Get the current method name dynamically
        String methodName = "method name: " + Thread.currentThread().getStackTrace()[2].getMethodName();

        // Determine the log file based on the log level automatically
        String logFileName;
        if (level.equalsIgnoreCase("ERROR")) {
            logFileName = className + "_error.log";
        } else {
            logFileName = className + "_info.log"; // For non-error messages, use info by default
        }

        // Try writing to the appropriate log file
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(LOG_FOLDER, logFileName), true))) {
            String logMessage = String.format("%s [%s]: %s %s", LocalDateTime.now(), methodName, level, message);
            writer.println(logMessage);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }
}