package com.example.testapi.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ErrorResponse model.
 * This class contains comprehensive tests for the ErrorResponse data model.
 */
@DisplayName("ErrorResponse Tests")
class ErrorResponseTest {

    @Test
    @DisplayName("Should create ErrorResponse with default constructor")
    void constructor_Default_ShouldCreateEmptyResponse() {
        // When
        ErrorResponse response = new ErrorResponse();

        // Then
        assertNotNull(response, "Response should not be null");
        assertNull(response.getError(), "Error should be null initially");
        assertNull(response.getMessage(), "Message should be null initially");
        assertNull(response.getTimestamp(), "Timestamp should be null initially");
    }

    @Test
    @DisplayName("Should create ErrorResponse with all parameters constructor")
    void constructor_AllParameters_ShouldCreateResponseWithValues() {
        // Given
        String error = "Bad Request";
        String message = "Test error message";
        String timestamp = "2024-01-01T12:00:00Z";

        // When
        ErrorResponse response = new ErrorResponse(error, message, timestamp);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(error, response.getError(), "Error should match");
        assertEquals(message, response.getMessage(), "Message should match");
        assertEquals(timestamp, response.getTimestamp(), "Timestamp should match");
    }

    @Test
    @DisplayName("Should set and get values correctly")
    void setAndGetValues_ShouldWorkCorrectly() {
        // Given
        ErrorResponse response = new ErrorResponse();
        String error = "Validation Error";
        String message = "Invalid input data";
        String timestamp = "2024-01-01T15:30:00Z";

        // When
        response.setError(error);
        response.setMessage(message);
        response.setTimestamp(timestamp);

        // Then
        assertEquals(error, response.getError(), "Error should be set and retrieved correctly");
        assertEquals(message, response.getMessage(), "Message should be set and retrieved correctly");
        assertEquals(timestamp, response.getTimestamp(), "Timestamp should be set and retrieved correctly");
    }

    @Test
    @DisplayName("Should create ErrorResponse using of method with message only")
    void of_WithMessageOnly_ShouldCreateResponseWithDefaultError() {
        // Given
        String message = "Test error message";

        // When
        ErrorResponse response = ErrorResponse.of(message);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals("Bad Request", response.getError(), "Error should be 'Bad Request'");
        assertEquals(message, response.getMessage(), "Message should match");
        assertNotNull(response.getTimestamp(), "Timestamp should not be null");
        assertTrue(response.getTimestamp().matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+Z"), 
                "Timestamp should be in ISO format");
    }

    @Test
    @DisplayName("Should create ErrorResponse using of method with error and message")
    void of_WithErrorAndMessage_ShouldCreateResponseWithCustomError() {
        // Given
        String error = "Custom Error";
        String message = "Custom error message";

        // When
        ErrorResponse response = ErrorResponse.of(error, message);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(error, response.getError(), "Error should match");
        assertEquals(message, response.getMessage(), "Message should match");
        assertNotNull(response.getTimestamp(), "Timestamp should not be null");
        assertTrue(response.getTimestamp().matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+Z"), 
                "Timestamp should be in ISO format");
    }

    @Test
    @DisplayName("Should generate different timestamps for multiple calls")
    void of_MultipleCalls_ShouldGenerateDifferentTimestamps() throws InterruptedException {
        // When
        ErrorResponse response1 = ErrorResponse.of("Test message 1");
        Thread.sleep(1); // Ensure different timestamps
        ErrorResponse response2 = ErrorResponse.of("Test message 2");

        // Then
        assertNotEquals(response1.getTimestamp(), response2.getTimestamp(), 
                "Timestamps should be different");
    }

    @Test
    @DisplayName("Should handle null message in of method")
    void of_WithNullMessage_ShouldCreateResponseWithNullMessage() {
        // When
        ErrorResponse response = ErrorResponse.of(null);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals("Bad Request", response.getError(), "Error should be 'Bad Request'");
        assertNull(response.getMessage(), "Message should be null");
        assertNotNull(response.getTimestamp(), "Timestamp should not be null");
    }

    @Test
    @DisplayName("Should handle null error in of method with two parameters")
    void of_WithNullError_ShouldCreateResponseWithNullError() {
        // Given
        String message = "Test message";

        // When
        ErrorResponse response = ErrorResponse.of(null, message);

        // Then
        assertNotNull(response, "Response should not be null");
        assertNull(response.getError(), "Error should be null");
        assertEquals(message, response.getMessage(), "Message should match");
        assertNotNull(response.getTimestamp(), "Timestamp should not be null");
    }

    @Test
    @DisplayName("ErrorResponse toString should contain all fields")
    void toString_ShouldContainAllFields() {
        // Given
        ErrorResponse response = new ErrorResponse("Test Error", "Test Message", "2024-01-01T12:00:00Z");

        // When
        String toString = response.toString();

        // Then
        assertTrue(toString.contains("error=Test Error"), "toString should contain error");
        assertTrue(toString.contains("message=Test Message"), "toString should contain message");
        assertTrue(toString.contains("timestamp=2024-01-01T12:00:00Z"), "toString should contain timestamp");
    }

    @Test
    @DisplayName("ErrorResponse equals should work correctly")
    void equals_ShouldWorkCorrectly() {
        // Given
        ErrorResponse response1 = new ErrorResponse("Error", "Message", "2024-01-01T12:00:00Z");
        ErrorResponse response2 = new ErrorResponse("Error", "Message", "2024-01-01T12:00:00Z");
        ErrorResponse response3 = new ErrorResponse("Different Error", "Message", "2024-01-01T12:00:00Z");

        // When & Then
        assertEquals(response1, response2, "Equal responses should be equal");
        assertNotEquals(response1, response3, "Different responses should not be equal");
        assertNotEquals(response1, null, "Response should not equal null");
        assertNotEquals(response1, "string", "Response should not equal string");
    }

    @Test
    @DisplayName("ErrorResponse hashCode should work correctly")
    void hashCode_ShouldWorkCorrectly() {
        // Given
        ErrorResponse response1 = new ErrorResponse("Error", "Message", "2024-01-01T12:00:00Z");
        ErrorResponse response2 = new ErrorResponse("Error", "Message", "2024-01-01T12:00:00Z");
        ErrorResponse response3 = new ErrorResponse("Different Error", "Message", "2024-01-01T12:00:00Z");

        // When & Then
        assertEquals(response1.hashCode(), response2.hashCode(), "Equal responses should have same hashCode");
        assertNotEquals(response1.hashCode(), response3.hashCode(), "Different responses should have different hashCode");
    }
}
