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
}
