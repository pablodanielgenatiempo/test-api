package com.example.testapi;

import com.example.testapi.model.PedidoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
}
