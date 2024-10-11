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
        String logFileName = className + ".log"; // Each class gets its own log file
        String methodName = "method name " + Thread.currentThread().getStackTrace()[2].getMethodName(); // current method name
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(LOG_FOLDER, logFileName), true))) {
            String logMessage = String.format("%s [%s]: %s %s", LocalDateTime.now(), methodName, level, message);
            writer.println(logMessage);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }
}