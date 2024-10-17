package org.CCT.Exceptions;

// Custom exception to handle errors related to customer data processing
public class CustomerDataException extends Exception {
    // Constructor that accepts an error message and passes it to the superclass
    public CustomerDataException(String message) {
        super(message);
    }
}