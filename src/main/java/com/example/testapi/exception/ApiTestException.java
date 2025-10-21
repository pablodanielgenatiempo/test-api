package com.example.testapi.exception;

/**
 * Custom exception for API test operations.
 * This exception is thrown when negative differences are calculated between MEP and crypto values,
 * indicating that crypto values are higher than MEP values for certain exchange rate types.
 * 
 * The exception message contains details about which specific items (avg, sell, buy) 
 * generated the negative differences.
 */
public class ApiTestException extends RuntimeException {

    /**
     * Constructs a new ApiTestException with the specified detail message.
     * 
     * @param message the detail message explaining which items caused negative differences
     */
    public ApiTestException(String message) {
        super(message);
    }

    /**
     * Constructs a new ApiTestException with the specified detail message and cause.
     * 
     * @param message the detail message explaining which items caused negative differences
     * @param cause the cause of this exception
     */
    public ApiTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
