package com.example.testapi.service;

import com.example.testapi.model.CotizacionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CotizacionService.
 * This class contains comprehensive tests for the CotizacionService business logic.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CotizacionService Tests")
class CotizacionServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private CotizacionService cotizacionService;

    @BeforeEach
    void setUp() {
        // Setup WebClient.Builder mock
        when(webClientBuilder.build()).thenReturn(webClient);
        
        // Setup WebClient mock chain
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Create service instance with mocked WebClient.Builder
        cotizacionService = new CotizacionService(webClientBuilder);
        
        // Configure service with test URL
        cotizacionService.setBluelyticsApiUrl("https://api.bluelytics.com.ar/v2/latest");
    }

    @Test
    @DisplayName("Should successfully retrieve exchange rate data")
    void obtenerCotizacion_ShouldReturnExchangeRateData() {
        // Given
        CotizacionResponse expectedResponse = createMockCotizacionResponse();
        when(responseSpec.bodyToMono(CotizacionResponse.class))
                .thenReturn(Mono.just(expectedResponse));

        // When
        CotizacionResponse result = cotizacionService.obtenerCotizacion();

        // Then
        assertNotNull(result, "Response should not be null");
        assertEquals(expectedResponse.getLastUpdate(), result.getLastUpdate());
        assertNotNull(result.getOficial());
        assertNotNull(result.getBlue());
        
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri("https://api.bluelytics.com.ar/v2/latest");
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(CotizacionResponse.class);
    }

    @Test
    @DisplayName("Should handle WebClientResponseException")
    void obtenerCotizacion_ShouldHandleWebClientResponseException() {
        // Given
        WebClientResponseException exception = mock(WebClientResponseException.class);
        when(responseSpec.bodyToMono(CotizacionResponse.class))
                .thenReturn(Mono.error(exception));

        // When & Then
        RuntimeException thrownException = assertThrows(
                RuntimeException.class,
                () -> cotizacionService.obtenerCotizacion(),
                "Should throw RuntimeException when WebClientResponseException occurs"
        );

        assertTrue(thrownException.getMessage().contains("Error retrieving exchange rate data from external API"));
        assertNotNull(thrownException.getCause());
        assertEquals(exception, thrownException.getCause());
    }

    @Test
    @DisplayName("Should handle generic exception")
    void obtenerCotizacion_ShouldHandleGenericException() {
        // Given
        RuntimeException exception = new RuntimeException("Generic error");
        when(responseSpec.bodyToMono(CotizacionResponse.class))
                .thenReturn(Mono.error(exception));

        // When & Then
        RuntimeException thrownException = assertThrows(
                RuntimeException.class,
                () -> cotizacionService.obtenerCotizacion(),
                "Should throw RuntimeException when generic exception occurs"
        );

        assertTrue(thrownException.getMessage().contains("Unexpected error occurred while retrieving exchange rate data"));
        assertNotNull(thrownException.getCause());
        assertEquals(exception, thrownException.getCause());
    }

    @Test
    @DisplayName("Should return response with all exchange rate types")
    void obtenerCotizacion_ShouldReturnAllExchangeRateTypes() {
        // Given
        CotizacionResponse expectedResponse = createMockCotizacionResponse();
        when(responseSpec.bodyToMono(CotizacionResponse.class))
                .thenReturn(Mono.just(expectedResponse));

        // When
        CotizacionResponse result = cotizacionService.obtenerCotizacion();

        // Then
        assertNotNull(result.getOficial(), "Oficial exchange rate should not be null");
        assertNotNull(result.getBlue(), "Blue exchange rate should not be null");
        assertNotNull(result.getOficialEuro(), "Oficial Euro exchange rate should not be null");
        assertNotNull(result.getBlueEuro(), "Blue Euro exchange rate should not be null");
        assertNotNull(result.getLastUpdate(), "Last update should not be null");
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
