package org.CCT.EnumFiles;

// Enum representing the different file types supported by the application
public enum FileType {

    TXT, // Represents a text file type
    CSV, // Represents a comma-separated values file type
    JSON; // Represents a JSON file type

    // Converts a string extension to the corresponding FileType enum value
    public static FileType fromExtension(String extension) {
        // Converts the provided extension to uppercase and returns the corresponding enum value
        return FileType.valueOf(extension.toUpperCase());
    }
}