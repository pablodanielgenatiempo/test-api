package com.example.testapi.controller;

import com.example.testapi.model.PedidoResponse;
import com.example.testapi.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PedidoController.
 * This class contains comprehensive tests for the PedidoController REST endpoints.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PedidoController Tests")
class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        // Setup method runs before each test
    }

    @Test
    @DisplayName("Should return OK response when processing order successfully")
    void procesarPedido_ShouldReturnOkResponse() {
        // Given
        PedidoResponse expectedResponse = new PedidoResponse("El pedido fue procesado");
        when(pedidoService.procesarPedido()).thenReturn(expectedResponse);

        // When
        ResponseEntity<PedidoResponse> result = pedidoController.procesarPedido();

        // Then
        assertNotNull(result, "Response entity should not be null");
        assertEquals(HttpStatus.OK, result.getStatusCode(), "Status should be OK");
        assertNotNull(result.getBody(), "Response body should not be null");
        assertEquals(expectedResponse.getMensaje(), result.getBody().getMensaje());
        
        verify(pedidoService, times(1)).procesarPedido();
    }

    @Test
    @DisplayName("Should return internal server error when service throws exception")
    void procesarPedido_ShouldReturnInternalServerErrorWhenServiceThrowsException() {
        // Given
        when(pedidoService.procesarPedido()).thenThrow(new RuntimeException("Service error"));

        // When
        ResponseEntity<PedidoResponse> result = pedidoController.procesarPedido();

        // Then
        assertNotNull(result, "Response entity should not be null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode(), 
                    "Status should be INTERNAL_SERVER_ERROR");
        assertNull(result.getBody(), "Response body should be null");
        
        verify(pedidoService, times(1)).procesarPedido();
    }

    @Test
    @DisplayName("Should call service method exactly once")
    void procesarPedido_ShouldCallServiceMethodOnce() {
        // Given
        PedidoResponse expectedResponse = new PedidoResponse("El pedido fue procesado");
        when(pedidoService.procesarPedido()).thenReturn(expectedResponse);

        // When
        pedidoController.procesarPedido();

        // Then
        verify(pedidoService, times(1)).procesarPedido();
        verifyNoMoreInteractions(pedidoService);
    }

    @Test
    @DisplayName("Should return response with correct message content")
    void procesarPedido_ShouldReturnCorrectMessageContent() {
        // Given
        String expectedMessage = "El pedido fue procesado";
        PedidoResponse expectedResponse = new PedidoResponse(expectedMessage);
        when(pedidoService.procesarPedido()).thenReturn(expectedResponse);

        // When
        ResponseEntity<PedidoResponse> result = pedidoController.procesarPedido();

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().getMensaje());
        assertTrue(result.getBody().getMensaje().contains("pedido"));
        assertTrue(result.getBody().getMensaje().contains("procesado"));
    }
}
