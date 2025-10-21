package com.example.testapi.service;

import com.example.testapi.model.PedidoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PedidoService.
 * This class contains comprehensive tests for the PedidoService business logic.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PedidoService Tests")
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        // Setup method runs before each test
    }

    @Test
    @DisplayName("Should process order and return success message")
    void procesarPedido_ShouldReturnSuccessMessage() {
        // Given
        String expectedMessage = "El pedido fue procesado";

        // When
        PedidoResponse result = pedidoService.procesarPedido();

        // Then
        assertNotNull(result, "Response should not be null");
        assertEquals(expectedMessage, result.getMensaje(), "Message should match expected value");
    }

    @Test
    @DisplayName("Should return non-null response object")
    void procesarPedido_ShouldReturnNonNullResponse() {
        // When
        PedidoResponse result = pedidoService.procesarPedido();

        // Then
        assertNotNull(result, "Response object should not be null");
        assertNotNull(result.getMensaje(), "Message should not be null");
        assertFalse(result.getMensaje().isEmpty(), "Message should not be empty");
    }

    @Test
    @DisplayName("Should return response with correct structure")
    void procesarPedido_ShouldReturnCorrectStructure() {
        // When
        PedidoResponse result = pedidoService.procesarPedido();

        // Then
        assertNotNull(result, "Response should not be null");
        assertTrue(result.getMensaje().contains("pedido"), "Message should contain 'pedido'");
        assertTrue(result.getMensaje().contains("procesado"), "Message should contain 'procesado'");
    }
}
