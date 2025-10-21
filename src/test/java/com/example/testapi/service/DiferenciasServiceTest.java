package com.example.testapi.service;

import com.example.testapi.exception.ApiTestException;
import com.example.testapi.model.DiferenciasRequest;
import com.example.testapi.model.DiferenciasResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DiferenciasService with EnumMap structure.
 * This class contains comprehensive tests for the DiferenciasService business logic,
 * including successful calculations, validation errors, and exception handling.
 */
@DisplayName("DiferenciasService Tests")
class DiferenciasServiceTest {

    private DiferenciasService diferenciasService;
    private DiferenciasRequest validRequest;

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
        diferenciasService = new DiferenciasService();
        
        // Create valid request with MEP > Crypto (positive differences)
        validRequest = createRequest(940.0, 945.0, 935.0, 1250.0, 1260.0, 1240.0);
    }

    @Test
    @DisplayName("Should calculate differences successfully when MEP > Crypto")
    void calcularDiferencias_WithValidData_ShouldCalculateDifferencesSuccessfully() {
        // When
        DiferenciasResponse response = diferenciasService.calcularDiferencias(validRequest);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(310.0, response.getDiferenciaAvg(), "Average difference should be 310.0");
        assertEquals(315.0, response.getDiferenciaSell(), "Sell difference should be 315.0");
        assertEquals(305.0, response.getDiferenciaBuy(), "Buy difference should be 305.0");
    }

    @Test
    @DisplayName("Should calculate differences with equal values")
    void calcularDiferencias_WithEqualValues_ShouldCalculateZeroDifferences() {
        // Given
        DiferenciasRequest request = createRequest(100.0, 100.0, 100.0, 100.0, 100.0, 100.0);

        // When
        DiferenciasResponse response = diferenciasService.calcularDiferencias(request);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(0.0, response.getDiferenciaAvg(), "Average difference should be 0.0");
        assertEquals(0.0, response.getDiferenciaSell(), "Sell difference should be 0.0");
        assertEquals(0.0, response.getDiferenciaBuy(), "Buy difference should be 0.0");
    }

    @Test
    @DisplayName("Should throw ApiTestException when average difference is negative")
    void calcularDiferencias_WithNegativeAvgDifference_ShouldThrowApiTestException() {
        // Given
        DiferenciasRequest request = createRequest(200.0, 100.0, 100.0, 100.0, 200.0, 200.0);

        // When & Then
        ApiTestException exception = assertThrows(ApiTestException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("Negative differences found for items: avg", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ApiTestException when sell difference is negative")
    void calcularDiferencias_WithNegativeSellDifference_ShouldThrowApiTestException() {
        // Given
        DiferenciasRequest request = createRequest(100.0, 200.0, 100.0, 200.0, 100.0, 200.0);

        // When & Then
        ApiTestException exception = assertThrows(ApiTestException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("Negative differences found for items: sell", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ApiTestException when buy difference is negative")
    void calcularDiferencias_WithNegativeBuyDifference_ShouldThrowApiTestException() {
        // Given
        DiferenciasRequest request = createRequest(100.0, 100.0, 200.0, 200.0, 200.0, 100.0);

        // When & Then
        ApiTestException exception = assertThrows(ApiTestException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("Negative differences found for items: buy", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ApiTestException when multiple differences are negative")
    void calcularDiferencias_WithMultipleNegativeDifferences_ShouldThrowApiTestException() {
        // Given
        DiferenciasRequest request = createRequest(200.0, 200.0, 200.0, 100.0, 100.0, 100.0);

        // When & Then
        ApiTestException exception = assertThrows(ApiTestException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("Negative differences found for items: avg, sell, buy", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when request is null")
    void calcularDiferencias_WithNullRequest_ShouldThrowIllegalArgumentException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(null);
        });

        assertEquals("Request cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when rates map is null")
    void calcularDiferencias_WithNullRatesMap_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = new DiferenciasRequest(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("Rates map cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when crypto data is missing")
    void calcularDiferencias_WithMissingCryptoData_ShouldThrowIllegalArgumentException() {
        // Given
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates = 
            DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.MEP, new DiferenciasRequest.ExchangeRateData(100.0, 100.0, 100.0));
        DiferenciasRequest request = new DiferenciasRequest(rates);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("Crypto data is required", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when MEP data is missing")
    void calcularDiferencias_WithMissingMepData_ShouldThrowIllegalArgumentException() {
        // Given
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates = 
            DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.CRYPTO, new DiferenciasRequest.ExchangeRateData(100.0, 100.0, 100.0));
        DiferenciasRequest request = new DiferenciasRequest(rates);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("MEP data is required", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when crypto avg value is null")
    void calcularDiferencias_WithNullCryptoAvg_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = createRequest(null, 100.0, 100.0, 200.0, 200.0, 200.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("crypto value_avg cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when crypto sell value is null")
    void calcularDiferencias_WithNullCryptoSell_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = createRequest(100.0, null, 100.0, 200.0, 200.0, 200.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("crypto value_sell cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when crypto buy value is null")
    void calcularDiferencias_WithNullCryptoBuy_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = createRequest(100.0, 100.0, null, 200.0, 200.0, 200.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("crypto value_buy cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when MEP avg value is null")
    void calcularDiferencias_WithNullMepAvg_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = createRequest(100.0, 100.0, 100.0, null, 200.0, 200.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("MEP value_avg cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when MEP sell value is null")
    void calcularDiferencias_WithNullMepSell_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = createRequest(100.0, 100.0, 100.0, 200.0, null, 200.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("MEP value_sell cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when MEP buy value is null")
    void calcularDiferencias_WithNullMepBuy_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = createRequest(100.0, 100.0, 100.0, 200.0, 200.0, null);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("MEP value_buy cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when crypto avg value is zero")
    void calcularDiferencias_WithZeroCryptoAvg_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = createRequest(0.0, 100.0, 100.0, 200.0, 200.0, 200.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("crypto value_avg must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when crypto avg value is negative")
    void calcularDiferencias_WithNegativeCryptoAvg_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = createRequest(-100.0, 100.0, 100.0, 200.0, 200.0, 200.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("crypto value_avg must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when MEP avg value is zero")
    void calcularDiferencias_WithZeroMepAvg_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = createRequest(100.0, 100.0, 100.0, 0.0, 200.0, 200.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("MEP value_avg must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when MEP avg value is negative")
    void calcularDiferencias_WithNegativeMepAvg_ShouldThrowIllegalArgumentException() {
        // Given
        DiferenciasRequest request = createRequest(100.0, 100.0, 100.0, -200.0, 200.0, 200.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diferenciasService.calcularDiferencias(request);
        });

        assertEquals("MEP value_avg must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should calculate differences with decimal values")
    void calcularDiferencias_WithDecimalValues_ShouldCalculateDifferencesCorrectly() {
        // Given
        DiferenciasRequest request = createRequest(100.5, 101.25, 99.75, 200.75, 201.5, 200.0);

        // When
        DiferenciasResponse response = diferenciasService.calcularDiferencias(request);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(100.25, response.getDiferenciaAvg(), 0.01, "Average difference should be 100.25");
        assertEquals(100.25, response.getDiferenciaSell(), 0.01, "Sell difference should be 100.25");
        assertEquals(100.25, response.getDiferenciaBuy(), 0.01, "Buy difference should be 100.25");
    }

    @Test
    @DisplayName("Should calculate differences with very large values")
    void calcularDiferencias_WithLargeValues_ShouldCalculateDifferencesCorrectly() {
        // Given
        DiferenciasRequest request = createRequest(1000000.0, 1000001.0, 999999.0, 2000000.0, 2000001.0, 1999999.0);

        // When
        DiferenciasResponse response = diferenciasService.calcularDiferencias(request);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(1000000.0, response.getDiferenciaAvg(), "Average difference should be 1000000.0");
        assertEquals(1000000.0, response.getDiferenciaSell(), "Sell difference should be 1000000.0");
        assertEquals(1000000.0, response.getDiferenciaBuy(), "Buy difference should be 1000000.0");
    }

    @Test
    @DisplayName("Should calculate differences with very small values")
    void calcularDiferencias_WithSmallValues_ShouldCalculateDifferencesCorrectly() {
        // Given
        DiferenciasRequest request = createRequest(0.001, 0.002, 0.0005, 0.01, 0.011, 0.009);

        // When
        DiferenciasResponse response = diferenciasService.calcularDiferencias(request);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(0.009, response.getDiferenciaAvg(), 0.0001, "Average difference should be 0.009");
        assertEquals(0.009, response.getDiferenciaSell(), 0.0001, "Sell difference should be 0.009");
        assertEquals(0.0085, response.getDiferenciaBuy(), 0.0001, "Buy difference should be 0.0085");
    }
}