package com.example.testapi;

import com.example.testapi.model.CotizacionResponse;
import com.example.testapi.model.DiferenciasRequest;
import com.example.testapi.model.DiferenciasResponse;
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

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    // ========== DIFERENCIAS ENDPOINT TESTS ==========

    @Test
    @DisplayName("Should calculate differences successfully via POST /api/v1/diferencias")
    void postDiferencias_WithValidRequest_ShouldReturnOkResponse() throws Exception {
        // Given
        DiferenciasRequest request = createValidDiferenciasRequest();

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.diferencia_avg").value(310.0))
                .andExpect(jsonPath("$.diferencia_sell").value(315.0))
                .andExpect(jsonPath("$.diferencia_buy").value(305.0));
    }

    @Test
    @DisplayName("Should return bad request with error message when differences are negative")
    void postDiferencias_WithNegativeDifferences_ShouldReturnBadRequestWithError() throws Exception {
        // Given
        DiferenciasRequest request = createRequestWithNegativeDifferences();

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Negative differences detected"))
                .andExpect(jsonPath("$.message").value("Negative differences found for items: avg"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should return bad request when rates map is null")
    void postDiferencias_WithNullRates_ShouldReturnBadRequest() throws Exception {
        // Given
        DiferenciasRequest request = new DiferenciasRequest(null);

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.message").value("Rates map cannot be null"));
    }

    @Test
    @DisplayName("Should return bad request when crypto data is missing")
    void postDiferencias_WithMissingCryptoData_ShouldReturnBadRequest() throws Exception {
        // Given
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates =
                DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.MEP, 
                new DiferenciasRequest.ExchangeRateData(100.0, 100.0, 100.0));
        DiferenciasRequest request = new DiferenciasRequest(rates);

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.message").value("Crypto data is required"));
    }

    @Test
    @DisplayName("Should return bad request when MEP data is missing")
    void postDiferencias_WithMissingMepData_ShouldReturnBadRequest() throws Exception {
        // Given
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates =
                DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.CRYPTO, 
                new DiferenciasRequest.ExchangeRateData(100.0, 100.0, 100.0));
        DiferenciasRequest request = new DiferenciasRequest(rates);

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.message").value("MEP data is required"));
    }

    @Test
    @DisplayName("Should return bad request when request body is invalid JSON")
    void postDiferencias_WithInvalidJson_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return unsupported media type when content type is not JSON")
    void postDiferencias_WithNonJsonContentType_ShouldReturnUnsupportedMediaType() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("some text"))
                .andExpect(status().isUnsupportedMediaType());
    }

    // ========== HTTP METHOD TESTS ==========

    @Test
    @DisplayName("Should return method not allowed for POST to /api/v1/pedido")
    void postPedido_ShouldReturnMethodNotAllowed() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Should return method not allowed for POST to /api/v1/cotizacion")
    void postCotizacion_ShouldReturnMethodNotAllowed() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/cotizacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Should return method not allowed for GET to /api/v1/diferencias")
    void getDiferencias_ShouldReturnMethodNotAllowed() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Should return method not allowed for PUT to /api/v1/pedido")
    void putPedido_ShouldReturnMethodNotAllowed() throws Exception {
        // When & Then
        mockMvc.perform(put("/api/v1/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Should return method not allowed for DELETE to /api/v1/cotizacion")
    void deleteCotizacion_ShouldReturnMethodNotAllowed() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/cotizacion"))
                .andExpect(status().isMethodNotAllowed());
    }

    // ========== CONTENT TYPE TESTS ==========

    @Test
    @DisplayName("Should accept application/json for pedido endpoint")
    void getPedido_WithApplicationJson_ShouldAcceptRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Should accept wildcard content type for pedido endpoint")
    void getPedido_WithWildcardContentType_ShouldAcceptRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/pedido")
                        .contentType(MediaType.ALL)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should handle missing content type for pedido endpoint")
    void getPedido_WithMissingContentType_ShouldAcceptRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/pedido"))
                .andExpect(status().isOk());
    }

    // ========== JSON STRUCTURE VALIDATION TESTS ==========

    @Test
    @DisplayName("Should return complete cotizacion JSON structure")
    void getCotizacion_ShouldReturnCompleteJsonStructure() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/cotizacion")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.oficial").exists())
                .andExpect(jsonPath("$.oficial.value_avg").exists())
                .andExpect(jsonPath("$.oficial.value_sell").exists())
                .andExpect(jsonPath("$.oficial.value_buy").exists())
                .andExpect(jsonPath("$.blue").exists())
                .andExpect(jsonPath("$.blue.value_avg").exists())
                .andExpect(jsonPath("$.blue.value_sell").exists())
                .andExpect(jsonPath("$.blue.value_buy").exists())
                .andExpect(jsonPath("$.oficial_euro").exists())
                .andExpect(jsonPath("$.oficial_euro.value_avg").exists())
                .andExpect(jsonPath("$.oficial_euro.value_sell").exists())
                .andExpect(jsonPath("$.oficial_euro.value_buy").exists())
                .andExpect(jsonPath("$.blue_euro").exists())
                .andExpect(jsonPath("$.blue_euro.value_avg").exists())
                .andExpect(jsonPath("$.blue_euro.value_sell").exists())
                .andExpect(jsonPath("$.blue_euro.value_buy").exists())
                .andExpect(jsonPath("$.last_update").exists())
                .andExpect(jsonPath("$.last_update").isString());
    }

    @Test
    @DisplayName("Should return complete diferencias JSON structure for successful request")
    void postDiferencias_ShouldReturnCompleteJsonStructure() throws Exception {
        // Given
        DiferenciasRequest request = createValidDiferenciasRequest();

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.diferencia_avg").exists())
                .andExpect(jsonPath("$.diferencia_avg").isNumber())
                .andExpect(jsonPath("$.diferencia_sell").exists())
                .andExpect(jsonPath("$.diferencia_sell").isNumber())
                .andExpect(jsonPath("$.diferencia_buy").exists())
                .andExpect(jsonPath("$.diferencia_buy").isNumber());
    }

    @Test
    @DisplayName("Should return complete error JSON structure for bad request")
    void postDiferencias_ShouldReturnCompleteErrorJsonStructure() throws Exception {
        // Given
        DiferenciasRequest request = new DiferenciasRequest(null);

        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error").isString())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.timestamp").isString());
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    @DisplayName("Should handle empty request body for diferencias endpoint")
    void postDiferencias_WithEmptyBody_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle null request body for diferencias endpoint")
    void postDiferencias_WithNullBody_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/diferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle request with zero differences")
    void postDiferencias_WithZeroDifferences_ShouldReturnOkResponse() throws Exception {
        // Given
        DiferenciasRequest request = createRequestWithZeroDifferences();

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
    void postDiferencias_WithDecimalValues_ShouldReturnOkResponse() throws Exception {
        // Given
        DiferenciasRequest request = createRequestWithDecimalValues();

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

    // ========== HELPER METHODS ==========

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

    /**
     * Creates a valid DiferenciasRequest for testing purposes.
     *
     * @return a valid DiferenciasRequest object
     */
    private DiferenciasRequest createValidDiferenciasRequest() {
        DiferenciasRequest.ExchangeRateData crypto = new DiferenciasRequest.ExchangeRateData(940.0, 945.0, 935.0);
        DiferenciasRequest.ExchangeRateData mep = new DiferenciasRequest.ExchangeRateData(1250.0, 1260.0, 1240.0);

        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates =
                DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.CRYPTO, crypto);
        rates.put(DiferenciasRequest.ExchangeRateType.MEP, mep);

        return new DiferenciasRequest(rates);
    }

    /**
     * Creates a DiferenciasRequest that will result in negative differences.
     *
     * @return a DiferenciasRequest object with negative differences
     */
    private DiferenciasRequest createRequestWithNegativeDifferences() {
        DiferenciasRequest.ExchangeRateData crypto = new DiferenciasRequest.ExchangeRateData(940.0, 945.0, 935.0);
        DiferenciasRequest.ExchangeRateData mep = new DiferenciasRequest.ExchangeRateData(910.0, 1260.0, 1240.0);

        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates =
                DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.CRYPTO, crypto);
        rates.put(DiferenciasRequest.ExchangeRateType.MEP, mep);

        return new DiferenciasRequest(rates);
    }

    /**
     * Creates a DiferenciasRequest that will result in zero differences.
     *
     * @return a DiferenciasRequest object with zero differences
     */
    private DiferenciasRequest createRequestWithZeroDifferences() {
        DiferenciasRequest.ExchangeRateData crypto = new DiferenciasRequest.ExchangeRateData(100.0, 100.0, 100.0);
        DiferenciasRequest.ExchangeRateData mep = new DiferenciasRequest.ExchangeRateData(100.0, 100.0, 100.0);

        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates =
                DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.CRYPTO, crypto);
        rates.put(DiferenciasRequest.ExchangeRateType.MEP, mep);

        return new DiferenciasRequest(rates);
    }

    /**
     * Creates a DiferenciasRequest with decimal values for testing.
     *
     * @return a DiferenciasRequest object with decimal values
     */
    private DiferenciasRequest createRequestWithDecimalValues() {
        DiferenciasRequest.ExchangeRateData crypto = new DiferenciasRequest.ExchangeRateData(100.5, 101.25, 99.75);
        DiferenciasRequest.ExchangeRateData mep = new DiferenciasRequest.ExchangeRateData(200.75, 201.5, 200.0);

        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates =
                DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.CRYPTO, crypto);
        rates.put(DiferenciasRequest.ExchangeRateType.MEP, mep);

        return new DiferenciasRequest(rates);
    }
}
