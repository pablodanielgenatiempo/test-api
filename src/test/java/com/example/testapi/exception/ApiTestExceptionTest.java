package com.example.testapi.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ApiTestException.
 * This class contains comprehensive tests for the custom ApiTestException class,
 * including constructor variations and exception behavior.
 */
@DisplayName("ApiTestException Tests")
class ApiTestExceptionTest {

    @Test
    @DisplayName("Should create ApiTestException with message")
    void constructor_WithMessage_ShouldCreateExceptionWithMessage() {
        // Given
        String expectedMessage = "Negative differences found for items: avg, sell";

        // When
        ApiTestException exception = new ApiTestException(expectedMessage);

        // Then
        assertNotNull(exception, "Exception should not be null");
        assertEquals(expectedMessage, exception.getMessage(), "Message should match");
        assertNull(exception.getCause(), "Cause should be null");
    }

    @Test
    @DisplayName("Should create ApiTestException with null message")
    void constructor_WithNullMessage_ShouldCreateExceptionWithNullMessage() {
        // When
        ApiTestException exception = new ApiTestException(null);

        // Then
        assertNotNull(exception, "Exception should not be null");
        assertNull(exception.getMessage(), "Message should be null");
        assertNull(exception.getCause(), "Cause should be null");
    }

    @Test
    @DisplayName("Should create ApiTestException with empty message")
    void constructor_WithEmptyMessage_ShouldCreateExceptionWithEmptyMessage() {
        // Given
        String emptyMessage = "";

        // When
        ApiTestException exception = new ApiTestException(emptyMessage);

        // Then
        assertNotNull(exception, "Exception should not be null");
        assertEquals(emptyMessage, exception.getMessage(), "Message should be empty");
        assertNull(exception.getCause(), "Cause should be null");
    }

    @Test
    @DisplayName("Should create ApiTestException with message and cause")
    void constructor_WithMessageAndCause_ShouldCreateExceptionWithBoth() {
        // Given
        String expectedMessage = "Negative differences found for items: buy";
        RuntimeException cause = new RuntimeException("Root cause");

        // When
        ApiTestException exception = new ApiTestException(expectedMessage, cause);

        // Then
        assertNotNull(exception, "Exception should not be null");
        assertEquals(expectedMessage, exception.getMessage(), "Message should match");
        assertEquals(cause, exception.getCause(), "Cause should match");
    }

    @Test
    @DisplayName("Should create ApiTestException with null message and cause")
    void constructor_WithNullMessageAndCause_ShouldCreateExceptionWithCause() {
        // Given
        RuntimeException cause = new RuntimeException("Root cause");

        // When
        ApiTestException exception = new ApiTestException(null, cause);

        // Then
        assertNotNull(exception, "Exception should not be null");
        assertNull(exception.getMessage(), "Message should be null");
        assertEquals(cause, exception.getCause(), "Cause should match");
    }

    @Test
    @DisplayName("Should create ApiTestException with message and null cause")
    void constructor_WithMessageAndNullCause_ShouldCreateExceptionWithMessage() {
        // Given
        String expectedMessage = "Negative differences found for items: avg";

        // When
        ApiTestException exception = new ApiTestException(expectedMessage, null);

        // Then
        assertNotNull(exception, "Exception should not be null");
        assertEquals(expectedMessage, exception.getMessage(), "Message should match");
        assertNull(exception.getCause(), "Cause should be null");
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void inheritance_ShouldExtendRuntimeException() {
        // Given
        String message = "Test message";

        // When
        ApiTestException exception = new ApiTestException(message);

        // Then
        assertTrue(exception instanceof RuntimeException, "Should be instance of RuntimeException");
    }

    @Test
    @DisplayName("Should be throwable")
    void throwable_ShouldBeThrowable() {
        // Given
        String message = "Test message";

        // When
        ApiTestException exception = new ApiTestException(message);

        // Then
        assertTrue(exception instanceof Throwable, "Should be instance of Throwable");
    }

    @Test
    @DisplayName("Should throw exception when thrown")
    void throwing_ShouldThrowExceptionCorrectly() {
        // Given
        String message = "Negative differences found for items: avg, sell, buy";

        // When & Then
        ApiTestException exception = assertThrows(ApiTestException.class, () -> {
            throw new ApiTestException(message);
        });

        assertEquals(message, exception.getMessage(), "Exception message should match");
    }

    @Test
    @DisplayName("Should throw exception with cause when thrown")
    void throwing_WithCause_ShouldThrowExceptionCorrectly() {
        // Given
        String message = "Negative differences found for items: avg";
        RuntimeException cause = new RuntimeException("Root cause");

        // When & Then
        ApiTestException exception = assertThrows(ApiTestException.class, () -> {
            throw new ApiTestException(message, cause);
        });

        assertEquals(message, exception.getMessage(), "Exception message should match");
        assertEquals(cause, exception.getCause(), "Exception cause should match");
    }

    @Test
    @DisplayName("Should handle complex message with multiple items")
    void constructor_WithComplexMessage_ShouldHandleComplexMessage() {
        // Given
        String complexMessage = "Negative differences found for items: avg, sell, buy. " +
                              "Crypto values are higher than MEP values for these exchange rate types.";

        // When
        ApiTestException exception = new ApiTestException(complexMessage);

        // Then
        assertNotNull(exception, "Exception should not be null");
        assertEquals(complexMessage, exception.getMessage(), "Complex message should match");
    }
}
