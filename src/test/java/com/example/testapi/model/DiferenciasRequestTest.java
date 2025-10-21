package com.example.testapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DiferenciasRequest model with EnumMap structure.
 * This class contains comprehensive tests for the DiferenciasRequest data model,
 * including validation of the EnumMap-based structure and ExchangeRateData class.
 */
@DisplayName("DiferenciasRequest Tests")
class DiferenciasRequestTest {

    private DiferenciasRequest diferenciasRequest;
    private DiferenciasRequest.ExchangeRateData cryptoData;
    private DiferenciasRequest.ExchangeRateData mepData;
    private Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates;

    @BeforeEach
    void setUp() {
        cryptoData = new DiferenciasRequest.ExchangeRateData(940.0, 945.0, 935.0);
        mepData = new DiferenciasRequest.ExchangeRateData(1250.0, 1260.0, 1240.0);
        
        rates = DiferenciasRequest.createRatesMap();
        rates.put(DiferenciasRequest.ExchangeRateType.CRYPTO, cryptoData);
        rates.put(DiferenciasRequest.ExchangeRateType.MEP, mepData);
        
        diferenciasRequest = new DiferenciasRequest(rates);
    }

    @Test
    @DisplayName("Should create DiferenciasRequest with default constructor")
    void constructor_Default_ShouldCreateEmptyRequest() {
        // When
        DiferenciasRequest request = new DiferenciasRequest();

        // Then
        assertNotNull(request, "Request should not be null");
        assertNull(request.getRates(), "Rates should be null initially");
    }

    @Test
    @DisplayName("Should create DiferenciasRequest with rates map constructor")
    void constructor_WithRatesMap_ShouldCreateRequestWithData() {
        // Given
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> testRates = 
            DiferenciasRequest.createRatesMap();
        testRates.put(DiferenciasRequest.ExchangeRateType.CRYPTO, cryptoData);
        testRates.put(DiferenciasRequest.ExchangeRateType.MEP, mepData);

        // When
        DiferenciasRequest request = new DiferenciasRequest(testRates);

        // Then
        assertNotNull(request, "Request should not be null");
        assertNotNull(request.getRates(), "Rates should not be null");
        assertEquals(2, request.getRates().size(), "Should contain 2 exchange rate types");
        assertTrue(request.getRates().containsKey(DiferenciasRequest.ExchangeRateType.CRYPTO), 
                  "Should contain crypto data");
        assertTrue(request.getRates().containsKey(DiferenciasRequest.ExchangeRateType.MEP), 
                  "Should contain MEP data");
    }

    @Test
    @DisplayName("Should create rates map using factory method")
    void createRatesMap_ShouldReturnEnumMap() {
        // When
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> ratesMap = 
            DiferenciasRequest.createRatesMap();

        // Then
        assertNotNull(ratesMap, "Rates map should not be null");
        assertTrue(ratesMap.isEmpty(), "Rates map should be empty initially");
    }

    @Test
    @DisplayName("Should use builder pattern correctly")
    void builder_ShouldCreateRequestCorrectly() {
        // When
        DiferenciasRequest request = DiferenciasRequest.builder()
            .addCrypto(940.0, 945.0, 935.0)
            .addMep(1250.0, 1260.0, 1240.0)
            .build();

        // Then
        assertNotNull(request, "Request should not be null");
        assertNotNull(request.getRates(), "Rates should not be null");
        assertEquals(2, request.getRates().size(), "Should contain 2 exchange rate types");
        
        DiferenciasRequest.ExchangeRateData crypto = request.getRates().get(DiferenciasRequest.ExchangeRateType.CRYPTO);
        DiferenciasRequest.ExchangeRateData mep = request.getRates().get(DiferenciasRequest.ExchangeRateType.MEP);
        
        assertNotNull(crypto, "Crypto data should not be null");
        assertNotNull(mep, "MEP data should not be null");
        
        assertEquals(940.0, crypto.getValueAvg(), "Crypto avg should match");
        assertEquals(945.0, crypto.getValueSell(), "Crypto sell should match");
        assertEquals(935.0, crypto.getValueBuy(), "Crypto buy should match");
        
        assertEquals(1250.0, mep.getValueAvg(), "MEP avg should match");
        assertEquals(1260.0, mep.getValueSell(), "MEP sell should match");
        assertEquals(1240.0, mep.getValueBuy(), "MEP buy should match");
    }

    @Test
    @DisplayName("Should handle ExchangeRateType enum values")
    void exchangeRateType_ShouldHaveCorrectValues() {
        // Then
        assertEquals(2, DiferenciasRequest.ExchangeRateType.values().length, 
                    "Should have 2 enum values");
        assertEquals("CRYPTO", DiferenciasRequest.ExchangeRateType.CRYPTO.name(), 
                    "CRYPTO enum should have correct name");
        assertEquals("MEP", DiferenciasRequest.ExchangeRateType.MEP.name(), 
                    "MEP enum should have correct name");
    }

    @Test
    @DisplayName("Should get crypto data from rates map")
    void getRates_WithCryptoKey_ShouldReturnCryptoData() {
        // When
        DiferenciasRequest.ExchangeRateData crypto = diferenciasRequest.getRates()
            .get(DiferenciasRequest.ExchangeRateType.CRYPTO);

        // Then
        assertNotNull(crypto, "Crypto data should not be null");
        assertEquals(940.0, crypto.getValueAvg(), "Crypto avg should match");
        assertEquals(945.0, crypto.getValueSell(), "Crypto sell should match");
        assertEquals(935.0, crypto.getValueBuy(), "Crypto buy should match");
    }

    @Test
    @DisplayName("Should get MEP data from rates map")
    void getRates_WithMepKey_ShouldReturnMepData() {
        // When
        DiferenciasRequest.ExchangeRateData mep = diferenciasRequest.getRates()
            .get(DiferenciasRequest.ExchangeRateType.MEP);

        // Then
        assertNotNull(mep, "MEP data should not be null");
        assertEquals(1250.0, mep.getValueAvg(), "MEP avg should match");
        assertEquals(1260.0, mep.getValueSell(), "MEP sell should match");
        assertEquals(1240.0, mep.getValueBuy(), "MEP buy should match");
    }

    @Test
    @DisplayName("Should handle null rates map")
    void constructor_WithNullRates_ShouldAcceptNull() {
        // When
        DiferenciasRequest request = new DiferenciasRequest(null);

        // Then
        assertNotNull(request, "Request should not be null");
        assertNull(request.getRates(), "Rates should be null");
    }

    @Test
    @DisplayName("Should handle empty rates map")
    void constructor_WithEmptyRates_ShouldAcceptEmpty() {
        // Given
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> emptyRates = 
            DiferenciasRequest.createRatesMap();

        // When
        DiferenciasRequest request = new DiferenciasRequest(emptyRates);

        // Then
        assertNotNull(request, "Request should not be null");
        assertNotNull(request.getRates(), "Rates should not be null");
        assertTrue(request.getRates().isEmpty(), "Rates should be empty");
    }

    // ExchangeRateData tests
    @Test
    @DisplayName("Should create ExchangeRateData with default constructor")
    void exchangeRateData_DefaultConstructor_ShouldCreateEmptyData() {
        // When
        DiferenciasRequest.ExchangeRateData data = new DiferenciasRequest.ExchangeRateData();

        // Then
        assertNotNull(data, "Data should not be null");
        assertNull(data.getValueAvg(), "Avg should be null initially");
        assertNull(data.getValueSell(), "Sell should be null initially");
        assertNull(data.getValueBuy(), "Buy should be null initially");
    }

    @Test
    @DisplayName("Should create ExchangeRateData with all parameters constructor")
    void exchangeRateData_WithAllParameters_ShouldCreateDataWithValues() {
        // Given
        Double avg = 100.0;
        Double sell = 105.0;
        Double buy = 95.0;

        // When
        DiferenciasRequest.ExchangeRateData data = new DiferenciasRequest.ExchangeRateData(avg, sell, buy);

        // Then
        assertNotNull(data, "Data should not be null");
        assertEquals(avg, data.getValueAvg(), "Avg should match");
        assertEquals(sell, data.getValueSell(), "Sell should match");
        assertEquals(buy, data.getValueBuy(), "Buy should match");
    }

    @Test
    @DisplayName("Should set and get ExchangeRateData values")
    void exchangeRateData_SetAndGet_ShouldWorkCorrectly() {
        // Given
        DiferenciasRequest.ExchangeRateData data = new DiferenciasRequest.ExchangeRateData();
        Double avg = 200.0;
        Double sell = 205.0;
        Double buy = 195.0;

        // When
        data.setValueAvg(avg);
        data.setValueSell(sell);
        data.setValueBuy(buy);

        // Then
        assertEquals(avg, data.getValueAvg(), "Avg should be set correctly");
        assertEquals(sell, data.getValueSell(), "Sell should be set correctly");
        assertEquals(buy, data.getValueBuy(), "Buy should be set correctly");
    }

    @Test
    @DisplayName("Should handle null values in ExchangeRateData")
    void exchangeRateData_WithNullValues_ShouldAcceptNulls() {
        // When
        DiferenciasRequest.ExchangeRateData data = new DiferenciasRequest.ExchangeRateData(null, null, null);

        // Then
        assertNotNull(data, "Data should not be null");
        assertNull(data.getValueAvg(), "Avg should be null");
        assertNull(data.getValueSell(), "Sell should be null");
        assertNull(data.getValueBuy(), "Buy should be null");
    }

    @Test
    @DisplayName("Should handle zero values in ExchangeRateData")
    void exchangeRateData_WithZeroValues_ShouldAcceptZeros() {
        // When
        DiferenciasRequest.ExchangeRateData data = new DiferenciasRequest.ExchangeRateData(0.0, 0.0, 0.0);

        // Then
        assertNotNull(data, "Data should not be null");
        assertEquals(0.0, data.getValueAvg(), "Avg should be zero");
        assertEquals(0.0, data.getValueSell(), "Sell should be zero");
        assertEquals(0.0, data.getValueBuy(), "Buy should be zero");
    }

    @Test
    @DisplayName("Should handle negative values in ExchangeRateData")
    void exchangeRateData_WithNegativeValues_ShouldAcceptNegatives() {
        // When
        DiferenciasRequest.ExchangeRateData data = new DiferenciasRequest.ExchangeRateData(-100.0, -105.0, -95.0);

        // Then
        assertNotNull(data, "Data should not be null");
        assertEquals(-100.0, data.getValueAvg(), "Avg should be negative");
        assertEquals(-105.0, data.getValueSell(), "Sell should be negative");
        assertEquals(-95.0, data.getValueBuy(), "Buy should be negative");
    }

    @Test
    @DisplayName("Should generate correct toString for ExchangeRateData")
    void exchangeRateData_ToString_ShouldContainValues() {
        // Given
        DiferenciasRequest.ExchangeRateData data = new DiferenciasRequest.ExchangeRateData(100.0, 105.0, 95.0);

        // When
        String toString = data.toString();

        // Then
        assertNotNull(toString, "ToString should not be null");
        assertTrue(toString.contains("100.0"), "Should contain avg value");
        assertTrue(toString.contains("105.0"), "Should contain sell value");
        assertTrue(toString.contains("95.0"), "Should contain buy value");
    }

    @Test
    @DisplayName("Should generate correct toString for DiferenciasRequest")
    void toString_ShouldContainRates() {
        // When
        String toString = diferenciasRequest.toString();

        // Then
        assertNotNull(toString, "ToString should not be null");
        assertTrue(toString.contains("rates"), "Should contain rates field");
    }

    @Test
    @DisplayName("Should handle equals and hashCode correctly")
    void equalsAndHashCode_ShouldWorkCorrectly() {
        // Given
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates1 = 
            DiferenciasRequest.createRatesMap();
        rates1.put(DiferenciasRequest.ExchangeRateType.CRYPTO, cryptoData);
        rates1.put(DiferenciasRequest.ExchangeRateType.MEP, mepData);
        
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates2 = 
            DiferenciasRequest.createRatesMap();
        rates2.put(DiferenciasRequest.ExchangeRateType.CRYPTO, cryptoData);
        rates2.put(DiferenciasRequest.ExchangeRateType.MEP, mepData);
        
        DiferenciasRequest request1 = new DiferenciasRequest(rates1);
        DiferenciasRequest request2 = new DiferenciasRequest(rates2);

        // Then
        assertEquals(request1, request2, "Requests should be equal");
        assertEquals(request1.hashCode(), request2.hashCode(), "Hash codes should be equal");
    }
}