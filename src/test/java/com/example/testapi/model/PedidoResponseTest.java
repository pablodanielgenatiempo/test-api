package com.example.testapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PedidoResponse model.
 * This class contains comprehensive tests for the PedidoResponse data model.
 */
@DisplayName("PedidoResponse Tests")
class PedidoResponseTest {

    private PedidoResponse pedidoResponse;

    @BeforeEach
    void setUp() {
        pedidoResponse = new PedidoResponse();
    }

    @Test
    @DisplayName("Should create PedidoResponse with default constructor")
    void constructor_Default_ShouldCreateEmptyResponse() {
        // When
        PedidoResponse response = new PedidoResponse();

        // Then
        assertNotNull(response, "Response should not be null");
        assertNull(response.getMensaje(), "Message should be null initially");
    }

    @Test
    @DisplayName("Should create PedidoResponse with message constructor")
    void constructor_WithMessage_ShouldCreateResponseWithMessage() {
        // Given
        String expectedMessage = "Test message";

        // When
        PedidoResponse response = new PedidoResponse(expectedMessage);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(expectedMessage, response.getMensaje(), "Message should match expected value");
    }

    @Test
    @DisplayName("Should set and get message correctly")
    void setMensaje_ShouldSetMessageCorrectly() {
        // Given
        String expectedMessage = "El pedido fue procesado";

        // When
        pedidoResponse.setMensaje(expectedMessage);

        // Then
        assertEquals(expectedMessage, pedidoResponse.getMensaje(), "Message should be set correctly");
    }

    @Test
    @DisplayName("Should handle null message")
    void setMensaje_WithNull_ShouldHandleNullMessage() {
        // When
        pedidoResponse.setMensaje(null);

        // Then
        assertNull(pedidoResponse.getMensaje(), "Message should be null");
    }

    @Test
    @DisplayName("Should handle empty message")
    void setMensaje_WithEmptyString_ShouldHandleEmptyMessage() {
        // Given
        String emptyMessage = "";

        // When
        pedidoResponse.setMensaje(emptyMessage);

        // Then
        assertEquals(emptyMessage, pedidoResponse.getMensaje(), "Message should be empty string");
    }

    @Test
    @DisplayName("Should return correct string representation")
    void toString_ShouldReturnCorrectStringRepresentation() {
        // Given
        String message = "Test message";
        pedidoResponse.setMensaje(message);

        // When
        String result = pedidoResponse.toString();

        // Then
        assertNotNull(result, "String representation should not be null");
        assertTrue(result.contains("PedidoResponse"), "Should contain class name");
        assertTrue(result.contains(message), "Should contain the message");
    }

    @Test
    @DisplayName("Should handle long message")
    void setMensaje_WithLongMessage_ShouldHandleLongMessage() {
        // Given
        String longMessage = "This is a very long message that contains multiple words and should be handled correctly by the PedidoResponse class";

        // When
        pedidoResponse.setMensaje(longMessage);

        // Then
        assertEquals(longMessage, pedidoResponse.getMensaje(), "Long message should be handled correctly");
    }

    @Test
    @DisplayName("Should test equality between PedidoResponse objects")
    void equals_ShouldWorkCorrectly() {
        // Given
        PedidoResponse response1 = new PedidoResponse("Test message");
        PedidoResponse response2 = new PedidoResponse("Test message");
        PedidoResponse response3 = new PedidoResponse("Different message");
        PedidoResponse response4 = new PedidoResponse();

        // When & Then
        assertEquals(response1, response2, "Equal response objects should be equal");
        assertNotEquals(response1, response3, "Different response objects should not be equal");
        assertEquals(response1, response1, "Same object should be equal to itself");
        assertNotEquals(response1, response4, "Response with different messages should not be equal");
        assertNotEquals(response1, null, "Response should not equal null");
        assertNotEquals(response1, "string", "Response should not equal string");
    }

    @Test
    @DisplayName("Should test hashCode consistency")
    void hashCode_ShouldWorkCorrectly() {
        // Given
        PedidoResponse response1 = new PedidoResponse("Test message");
        PedidoResponse response2 = new PedidoResponse("Test message");
        PedidoResponse response3 = new PedidoResponse("Different message");

        // When & Then
        assertEquals(response1.hashCode(), response2.hashCode(), "Equal objects should have same hashCode");
        assertNotEquals(response1.hashCode(), response3.hashCode(), "Different objects should have different hashCode");
        assertEquals(response1.hashCode(), response1.hashCode(), "Same object should have same hashCode");
    }

    @Test
    @DisplayName("Should test canEqual method")
    void canEqual_ShouldWorkCorrectly() {
        // Given
        PedidoResponse response1 = new PedidoResponse();
        PedidoResponse response2 = new PedidoResponse();
        String message = "test";

        // When & Then
        assertTrue(response1.canEqual(response2), "PedidoResponse should be able to equal another PedidoResponse");
        assertFalse(response1.canEqual(message), "PedidoResponse should not be able to equal string");
        assertFalse(response1.canEqual(null), "PedidoResponse should not be able to equal null");
    }

    @Test
    @DisplayName("Should handle null messages in equality test")
    void equals_WithNullMessages_ShouldWorkCorrectly() {
        // Given
        PedidoResponse response1 = new PedidoResponse();
        PedidoResponse response2 = new PedidoResponse();
        PedidoResponse response3 = new PedidoResponse("test");

        // When & Then
        assertEquals(response1, response2, "Objects with null messages should be equal");
        assertNotEquals(response1, response3, "Objects with different null messages should not be equal");
        assertEquals(response1.hashCode(), response2.hashCode(), "Objects with null messages should have same hashCode");
    }

    @Test
    @DisplayName("Should handle empty messages in equality test")
    void equals_WithEmptyMessages_ShouldWorkCorrectly() {
        // Given
        PedidoResponse response1 = new PedidoResponse("");
        PedidoResponse response2 = new PedidoResponse("");
        PedidoResponse response3 = new PedidoResponse("test");

        // When & Then
        assertEquals(response1, response2, "Objects with empty messages should be equal");
        assertNotEquals(response1, response3, "Objects with different empty messages should not be equal");
        assertEquals(response1.hashCode(), response2.hashCode(), "Objects with empty messages should have same hashCode");
    }

    @Test
    @DisplayName("Should handle special characters in message")
    void setMensaje_WithSpecialCharacters_ShouldHandleSpecialCharacters() {
        // Given
        String specialMessage = "Mensaje con caracteres especiales: áéíóú ñü @#$%^&*()";

        // When
        pedidoResponse.setMensaje(specialMessage);

        // Then
        assertEquals(specialMessage, pedidoResponse.getMensaje(), "Special characters should be handled correctly");
    }

    @Test
    @DisplayName("Should handle whitespace-only message")
    void setMensaje_WithWhitespaceOnly_ShouldHandleWhitespaceOnly() {
        // Given
        String whitespaceMessage = "   \t\n   ";

        // When
        pedidoResponse.setMensaje(whitespaceMessage);

        // Then
        assertEquals(whitespaceMessage, pedidoResponse.getMensaje(), "Whitespace-only message should be handled correctly");
    }
}
