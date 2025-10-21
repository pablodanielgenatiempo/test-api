package com.example.testapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CotizacionResponse model.
 * This class contains comprehensive tests for the CotizacionResponse data model.
 */
@DisplayName("CotizacionResponse Tests")
class CotizacionResponseTest {

    private CotizacionResponse cotizacionResponse;

    @BeforeEach
    void setUp() {
        cotizacionResponse = new CotizacionResponse();
    }

    @Test
    @DisplayName("Should create CotizacionResponse with default constructor")
    void constructor_Default_ShouldCreateEmptyResponse() {
        // When
        CotizacionResponse response = new CotizacionResponse();

        // Then
        assertNotNull(response, "Response should not be null");
        assertNull(response.getOficial(), "Oficial should be null initially");
        assertNull(response.getBlue(), "Blue should be null initially");
        assertNull(response.getOficialEuro(), "OficialEuro should be null initially");
        assertNull(response.getBlueEuro(), "BlueEuro should be null initially");
        assertNull(response.getLastUpdate(), "LastUpdate should be null initially");
    }

    @Test
    @DisplayName("Should set and get oficial exchange rate data")
    void setOficial_ShouldSetOficialDataCorrectly() {
        // Given
        CotizacionResponse.CotizacionData oficialData = createMockCotizacionData(100.0, 99.5, 100.5);

        // When
        cotizacionResponse.setOficial(oficialData);

        // Then
        assertEquals(oficialData, cotizacionResponse.getOficial(), "Oficial data should be set correctly");
        assertEquals(100.0, cotizacionResponse.getOficial().getValueAvg(), "Average value should match");
        assertEquals(99.5, cotizacionResponse.getOficial().getValueBuy(), "Buy value should match");
        assertEquals(100.5, cotizacionResponse.getOficial().getValueSell(), "Sell value should match");
    }

    @Test
    @DisplayName("Should set and get blue exchange rate data")
    void setBlue_ShouldSetBlueDataCorrectly() {
        // Given
        CotizacionResponse.CotizacionData blueData = createMockCotizacionData(200.0, 199.5, 200.5);

        // When
        cotizacionResponse.setBlue(blueData);

        // Then
        assertEquals(blueData, cotizacionResponse.getBlue(), "Blue data should be set correctly");
        assertEquals(200.0, cotizacionResponse.getBlue().getValueAvg(), "Average value should match");
        assertEquals(199.5, cotizacionResponse.getBlue().getValueBuy(), "Buy value should match");
        assertEquals(200.5, cotizacionResponse.getBlue().getValueSell(), "Sell value should match");
    }

    @Test
    @DisplayName("Should set and get last update timestamp")
    void setLastUpdate_ShouldSetLastUpdateCorrectly() {
        // Given
        String expectedTimestamp = "2024-01-01T12:00:00Z";

        // When
        cotizacionResponse.setLastUpdate(expectedTimestamp);

        // Then
        assertEquals(expectedTimestamp, cotizacionResponse.getLastUpdate(), "Last update should be set correctly");
    }

    @Test
    @DisplayName("Should handle null values")
    void setValues_WithNull_ShouldHandleNullValues() {
        // When
        cotizacionResponse.setOficial(null);
        cotizacionResponse.setBlue(null);
        cotizacionResponse.setOficialEuro(null);
        cotizacionResponse.setBlueEuro(null);
        cotizacionResponse.setLastUpdate(null);

        // Then
        assertNull(cotizacionResponse.getOficial(), "Oficial should be null");
        assertNull(cotizacionResponse.getBlue(), "Blue should be null");
        assertNull(cotizacionResponse.getOficialEuro(), "OficialEuro should be null");
        assertNull(cotizacionResponse.getBlueEuro(), "BlueEuro should be null");
        assertNull(cotizacionResponse.getLastUpdate(), "LastUpdate should be null");
    }

    @Test
    @DisplayName("Should return correct string representation")
    void toString_ShouldReturnCorrectStringRepresentation() {
        // Given
        CotizacionResponse.CotizacionData oficialData = createMockCotizacionData(100.0, 99.5, 100.5);
        cotizacionResponse.setOficial(oficialData);
        cotizacionResponse.setLastUpdate("2024-01-01T12:00:00Z");

        // When
        String result = cotizacionResponse.toString();

        // Then
        assertNotNull(result, "String representation should not be null");
        assertTrue(result.contains("CotizacionResponse"), "Should contain class name");
        assertTrue(result.contains("2024-01-01T12:00:00Z"), "Should contain last update");
    }

    @Test
    @DisplayName("Should handle CotizacionData with all values")
    void cotizacionData_ShouldHandleAllValues() {
        // Given
        CotizacionResponse.CotizacionData data = new CotizacionResponse.CotizacionData();
        data.setValueAvg(150.0);
        data.setValueBuy(149.5);
        data.setValueSell(150.5);

        // When
        cotizacionResponse.setOficial(data);

        // Then
        assertNotNull(cotizacionResponse.getOficial(), "Oficial data should not be null");
        assertEquals(150.0, cotizacionResponse.getOficial().getValueAvg(), "Average value should match");
        assertEquals(149.5, cotizacionResponse.getOficial().getValueBuy(), "Buy value should match");
        assertEquals(150.5, cotizacionResponse.getOficial().getValueSell(), "Sell value should match");
    }

    @Test
    @DisplayName("Should handle CotizacionData toString")
    void cotizacionDataToString_ShouldReturnCorrectString() {
        // Given
        CotizacionResponse.CotizacionData data = new CotizacionResponse.CotizacionData();
        data.setValueAvg(100.0);
        data.setValueBuy(99.5);
        data.setValueSell(100.5);

        // When
        String result = data.toString();

        // Then
        assertNotNull(result, "String representation should not be null");
        assertTrue(result.contains("CotizacionData"), "Should contain class name");
        assertTrue(result.contains("100.0"), "Should contain average value");
        assertTrue(result.contains("99.5"), "Should contain buy value");
        assertTrue(result.contains("100.5"), "Should contain sell value");
    }

    /**
     * Creates a mock CotizacionData object for testing purposes.
     *
     * @param avg the average value
     * @param buy the buy value
     * @param sell the sell value
     * @return a mock CotizacionData object
     */
    private CotizacionResponse.CotizacionData createMockCotizacionData(Double avg, Double buy, Double sell) {
        CotizacionResponse.CotizacionData data = new CotizacionResponse.CotizacionData();
        data.setValueAvg(avg);
        data.setValueBuy(buy);
        data.setValueSell(sell);
        return data;
    }
}
