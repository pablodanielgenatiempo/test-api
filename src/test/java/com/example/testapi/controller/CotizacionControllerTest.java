package com.example.testapi.controller;

import com.example.testapi.model.CotizacionResponse;
import com.example.testapi.service.CotizacionService;
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
 * Unit tests for CotizacionController.
 * This class contains comprehensive tests for the CotizacionController REST endpoints.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CotizacionController Tests")
class CotizacionControllerTest {

    @Mock
    private CotizacionService cotizacionService;

    @InjectMocks
    private CotizacionController cotizacionController;

    @BeforeEach
    void setUp() {
        // Setup method runs before each test
    }

    @Test
    @DisplayName("Should return OK response when retrieving quotation successfully")
    void obtenerCotizacion_ShouldReturnOkResponse() {
        // Given
        CotizacionResponse expectedResponse = createMockCotizacionResponse();
        when(cotizacionService.obtenerCotizacion()).thenReturn(expectedResponse);

        // When
        ResponseEntity<CotizacionResponse> result = cotizacionController.obtenerCotizacion();

        // Then
        assertNotNull(result, "Response entity should not be null");
        assertEquals(HttpStatus.OK, result.getStatusCode(), "Status should be OK");
        assertNotNull(result.getBody(), "Response body should not be null");
        assertEquals(expectedResponse.getLastUpdate(), result.getBody().getLastUpdate());
        
        verify(cotizacionService, times(1)).obtenerCotizacion();
    }

    @Test
    @DisplayName("Should return internal server error when service throws RuntimeException")
    void obtenerCotizacion_ShouldReturnInternalServerErrorWhenServiceThrowsException() {
        // Given
        when(cotizacionService.obtenerCotizacion()).thenThrow(new RuntimeException("Service error"));

        // When
        ResponseEntity<CotizacionResponse> result = cotizacionController.obtenerCotizacion();

        // Then
        assertNotNull(result, "Response entity should not be null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode(), 
                    "Status should be INTERNAL_SERVER_ERROR");
        assertNull(result.getBody(), "Response body should be null");
        
        verify(cotizacionService, times(1)).obtenerCotizacion();
    }

    @Test
    @DisplayName("Should call service method exactly once")
    void obtenerCotizacion_ShouldCallServiceMethodOnce() {
        // Given
        CotizacionResponse expectedResponse = createMockCotizacionResponse();
        when(cotizacionService.obtenerCotizacion()).thenReturn(expectedResponse);

        // When
        cotizacionController.obtenerCotizacion();

        // Then
        verify(cotizacionService, times(1)).obtenerCotizacion();
        verifyNoMoreInteractions(cotizacionService);
    }

    @Test
    @DisplayName("Should return response with all exchange rate data")
    void obtenerCotizacion_ShouldReturnAllExchangeRateData() {
        // Given
        CotizacionResponse expectedResponse = createMockCotizacionResponse();
        when(cotizacionService.obtenerCotizacion()).thenReturn(expectedResponse);

        // When
        ResponseEntity<CotizacionResponse> result = cotizacionController.obtenerCotizacion();

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getOficial());
        assertNotNull(result.getBody().getBlue());
        assertNotNull(result.getBody().getOficialEuro());
        assertNotNull(result.getBody().getBlueEuro());
        assertNotNull(result.getBody().getLastUpdate());
    }

    @Test
    @DisplayName("Should handle service returning null response")
    void obtenerCotizacion_ShouldHandleNullResponse() {
        // Given
        when(cotizacionService.obtenerCotizacion()).thenReturn(null);

        // When
        ResponseEntity<CotizacionResponse> result = cotizacionController.obtenerCotizacion();

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
        
        verify(cotizacionService, times(1)).obtenerCotizacion();
    }

    /**
     * Creates a mock CotizacionResponse for testing purposes.
     *
     * @return a mock CotizacionResponse object
     */
    private CotizacionResponse createMockCotizacionResponse() {
        CotizacionResponse response = new CotizacionResponse();
        
        CotizacionResponse.CotizacionData oficial = new CotizacionResponse.CotizacionData();
        oficial.setValueAvg(100.0);
        oficial.setValueBuy(99.5);
        oficial.setValueSell(100.5);
        
        CotizacionResponse.CotizacionData blue = new CotizacionResponse.CotizacionData();
        blue.setValueAvg(200.0);
        blue.setValueBuy(199.5);
        blue.setValueSell(200.5);
        
        CotizacionResponse.CotizacionData oficialEuro = new CotizacionResponse.CotizacionData();
        oficialEuro.setValueAvg(110.0);
        oficialEuro.setValueBuy(109.5);
        oficialEuro.setValueSell(110.5);
        
        CotizacionResponse.CotizacionData blueEuro = new CotizacionResponse.CotizacionData();
        blueEuro.setValueAvg(220.0);
        blueEuro.setValueBuy(219.5);
        blueEuro.setValueSell(220.5);
        
        response.setOficial(oficial);
        response.setBlue(blue);
        response.setOficialEuro(oficialEuro);
        response.setBlueEuro(blueEuro);
        response.setLastUpdate("2024-01-01T12:00:00Z");
        
        return response;
    }
}
