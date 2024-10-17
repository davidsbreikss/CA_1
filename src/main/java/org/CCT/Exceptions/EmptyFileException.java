package org.CCT.Exceptions;

// Custom exception to indicate that a file is empty and cannot be processed
public class EmptyFileException extends Exception {
    // Constructor that accepts an error message and passes it to the superclass
    public EmptyFileException(String message) {
        super(message);
    }
}
