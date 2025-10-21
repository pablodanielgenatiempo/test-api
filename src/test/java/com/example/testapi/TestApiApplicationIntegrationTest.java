package com.example.testapi;

import com.example.testapi.model.CotizacionResponse;
import com.example.testapi.model.PedidoResponse;
import com.example.testapi.service.CotizacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the REST API endpoints.
 * This class contains comprehensive integration tests for the API endpoints.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
@ActiveProfiles("test")
@DisplayName("API Integration Tests")
class TestApiApplicationIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CotizacionService cotizacionService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Setup mock for CotizacionService
        CotizacionResponse mockResponse = createMockCotizacionResponse();
        when(cotizacionService.obtenerCotizacion()).thenReturn(mockResponse);
    }

    @Test
    @DisplayName("Should return pedido response when calling GET /api/v1/pedido")
    void getPedido_ShouldReturnPedidoResponse() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/pedido")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensaje").value("El pedido fue procesado"));
    }

    @Test
    @DisplayName("Should return cotizacion response when calling GET /api/v1/cotizacion")
    void getCotizacion_ShouldReturnCotizacionResponse() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/cotizacion")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.last_update").exists());
    }

    @Test
    @DisplayName("Should handle non-existent endpoint")
    void getNonExistentEndpoint_ShouldReturnNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/non-existent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should accept JSON content type")
    void getPedido_WithJsonContentType_ShouldAcceptRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Should return valid JSON structure for pedido")
    void getPedido_ShouldReturnValidJsonStructure() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/pedido")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.mensaje").isString())
                .andExpect(jsonPath("$.mensaje").isNotEmpty());
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
