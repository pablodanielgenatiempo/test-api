package com.example.testapi.controller;

import com.example.testapi.exception.ApiTestException;
import com.example.testapi.model.DiferenciasRequest;
import com.example.testapi.model.DiferenciasResponse;
import com.example.testapi.service.DiferenciasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for DiferenciasController with EnumMap structure.
 * This class contains comprehensive tests for the DiferenciasController REST endpoints,
 * including successful requests, validation errors, and exception handling.
 */
@WebMvcTest(DiferenciasController.class)
@DisplayName("DiferenciasController Tests")
class DiferenciasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiferenciasService diferenciasService;

    @Autowired
    private ObjectMapper objectMapper;

    private DiferenciasRequest validRequest;
    private DiferenciasResponse validResponse;

    /**
     * Helper method to create a DiferenciasRequest with crypto and MEP data.
     */
    private DiferenciasRequest createRequest(Double cryptoAvg, Double cryptoSell, Double cryptoBuy,
                                            Double mepAvg, Double mepSell, Double mepBuy) {
        DiferenciasRequest.ExchangeRateData crypto = new DiferenciasRequest.ExchangeRateData(cryptoAvg, cryptoSell, cryptoBuy);
        DiferenciasRequest.ExchangeRateData mep = new DiferenciasRequest.ExchangeRateData(mepAvg, mepSell, mepBuy);
        
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates = 
            DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.CRYPTO, crypto);
        rates.put(DiferenciasRequest.ExchangeRateType.MEP, mep);
        
        return new DiferenciasRequest(rates);
    }

    @BeforeEach
    void setUp() {
        // Create valid request
        validRequest = createRequest(940.0, 945.0, 935.0, 1250.0, 1260.0, 1240.0);

        // Create valid response
        validResponse = DiferenciasResponse.of(310.0, 315.0, 305.0);
    }

    @Test
    @DisplayName("Should calculate differences successfully")
    void calcularDiferencias_WithValidRequest_ShouldReturnOkResponse() throws Exception {
        // Given
        when(diferenciasService.calcularDiferencias(any(DiferenciasRequest.class)))
                .thenReturn(validResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.diferencia_avg").value(310.0))
                .andExpect(jsonPath("$.diferencia_sell").value(315.0))
                .andExpect(jsonPath("$.diferencia_buy").value(305.0));
    }

    @Test
    @DisplayName("Should return bad request with error message when ApiTestException is thrown")
    void calcularDiferencias_WithApiTestException_ShouldReturnBadRequestWithErrorMessage() throws Exception {
        // Given
        String errorMessage = "Negative differences found for items: avg, sell";
        when(diferenciasService.calcularDiferencias(any(DiferenciasRequest.class)))
                .thenThrow(new ApiTestException(errorMessage));

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Negative differences detected"))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should return bad request with error message when IllegalArgumentException is thrown")
    void calcularDiferencias_WithIllegalArgumentException_ShouldReturnBadRequestWithErrorMessage() throws Exception {
        // Given
        String errorMessage = "Request cannot be null";
        when(diferenciasService.calcularDiferencias(any(DiferenciasRequest.class)))
                .thenThrow(new IllegalArgumentException(errorMessage));

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should return internal server error with error message when unexpected exception is thrown")
    void calcularDiferencias_WithUnexpectedException_ShouldReturnInternalServerErrorWithErrorMessage() throws Exception {
        // Given
        when(diferenciasService.calcularDiferencias(any(DiferenciasRequest.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Internal server error"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should return bad request when request body is invalid JSON")
    void calcularDiferencias_WithInvalidJson_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when request body is empty")
    void calcularDiferencias_WithEmptyBody_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when crypto data is missing")
    void calcularDiferencias_WithMissingCryptoData_ShouldReturnBadRequest() throws Exception {
        // Given
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates = 
            DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.MEP, new DiferenciasRequest.ExchangeRateData(100.0, 100.0, 100.0));
        DiferenciasRequest requestWithoutCrypto = new DiferenciasRequest(rates);

        when(diferenciasService.calcularDiferencias(any(DiferenciasRequest.class)))
                .thenThrow(new IllegalArgumentException("Crypto data is required"));

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestWithoutCrypto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when MEP data is missing")
    void calcularDiferencias_WithMissingMepData_ShouldReturnBadRequest() throws Exception {
        // Given
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates = 
            DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.CRYPTO, new DiferenciasRequest.ExchangeRateData(100.0, 100.0, 100.0));
        DiferenciasRequest requestWithoutMep = new DiferenciasRequest(rates);

        when(diferenciasService.calcularDiferencias(any(DiferenciasRequest.class)))
                .thenThrow(new IllegalArgumentException("MEP data is required"));

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestWithoutMep)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle request with zero differences")
    void calcularDiferencias_WithZeroDifferences_ShouldReturnOkResponse() throws Exception {
        // Given
        DiferenciasRequest request = createRequest(100.0, 100.0, 100.0, 100.0, 100.0, 100.0);
        DiferenciasResponse response = DiferenciasResponse.of(0.0, 0.0, 0.0);

        when(diferenciasService.calcularDiferencias(any(DiferenciasRequest.class)))
                .thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.diferencia_avg").value(0.0))
                .andExpect(jsonPath("$.diferencia_sell").value(0.0))
                .andExpect(jsonPath("$.diferencia_buy").value(0.0));
    }

    @Test
    @DisplayName("Should handle request with decimal values")
    void calcularDiferencias_WithDecimalValues_ShouldReturnOkResponse() throws Exception {
        // Given
        DiferenciasRequest request = createRequest(100.5, 101.25, 99.75, 200.75, 201.5, 200.0);
        DiferenciasResponse response = DiferenciasResponse.of(100.25, 100.25, 100.25);

        when(diferenciasService.calcularDiferencias(any(DiferenciasRequest.class)))
                .thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.diferencia_avg").value(100.25))
                .andExpect(jsonPath("$.diferencia_sell").value(100.25))
                .andExpect(jsonPath("$.diferencia_buy").value(100.25));
    }

    @Test
    @DisplayName("Should return unsupported media type when content type is not JSON")
    void calcularDiferencias_WithNonJsonContentType_ShouldReturnUnsupportedMediaType() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("some text"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("Should return bad request when request is null")
    void calcularDiferencias_WithNullRequest_ShouldReturnBadRequest() throws Exception {
        // Given
        when(diferenciasService.calcularDiferencias(any()))
                .thenThrow(new IllegalArgumentException("Request cannot be null"));

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle request with empty rates map")
    void calcularDiferencias_WithEmptyRatesMap_ShouldReturnBadRequest() throws Exception {
        // Given
        DiferenciasRequest request = new DiferenciasRequest(DiferenciasRequest.createRatesMap());

        when(diferenciasService.calcularDiferencias(any(DiferenciasRequest.class)))
                .thenThrow(new IllegalArgumentException("Crypto data is required"));

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle request with null rates map")
    void calcularDiferencias_WithNullRatesMap_ShouldReturnBadRequest() throws Exception {
        // Given
        DiferenciasRequest request = new DiferenciasRequest(null);

        when(diferenciasService.calcularDiferencias(any(DiferenciasRequest.class)))
                .thenThrow(new IllegalArgumentException("Rates map cannot be null"));

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}