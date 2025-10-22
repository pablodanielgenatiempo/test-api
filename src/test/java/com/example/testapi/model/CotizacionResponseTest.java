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

    @Test
    @DisplayName("Should test equality between CotizacionResponse objects")
    void equals_ShouldWorkCorrectly() {
        // Given
        CotizacionResponse.CotizacionData oficialData1 = createMockCotizacionData(100.0, 99.5, 100.5);
        CotizacionResponse.CotizacionData blueData1 = createMockCotizacionData(200.0, 199.5, 200.5);
        
        CotizacionResponse.CotizacionData oficialData2 = createMockCotizacionData(100.0, 99.5, 100.5);
        CotizacionResponse.CotizacionData blueData2 = createMockCotizacionData(200.0, 199.5, 200.5);
        
        CotizacionResponse response1 = new CotizacionResponse();
        response1.setOficial(oficialData1);
        response1.setBlue(blueData1);
        response1.setLastUpdate("2024-01-01T12:00:00Z");
        
        CotizacionResponse response2 = new CotizacionResponse();
        response2.setOficial(oficialData2);
        response2.setBlue(blueData2);
        response2.setLastUpdate("2024-01-01T12:00:00Z");
        
        CotizacionResponse response3 = new CotizacionResponse();
        response3.setOficial(oficialData1);
        response3.setBlue(blueData1);
        response3.setLastUpdate("2024-01-01T13:00:00Z");

        // When & Then
        assertEquals(response1, response2, "Equal response objects should be equal");
        assertNotEquals(response1, response3, "Different response objects should not be equal");
        assertEquals(response1, response1, "Same object should be equal to itself");
        assertNotEquals(response1, null, "Response should not equal null");
        assertNotEquals(response1, "string", "Response should not equal string");
    }

    @Test
    @DisplayName("Should test hashCode consistency")
    void hashCode_ShouldWorkCorrectly() {
        // Given
        CotizacionResponse.CotizacionData oficialData1 = createMockCotizacionData(100.0, 99.5, 100.5);
        CotizacionResponse.CotizacionData blueData1 = createMockCotizacionData(200.0, 199.5, 200.5);
        
        CotizacionResponse.CotizacionData oficialData2 = createMockCotizacionData(100.0, 99.5, 100.5);
        CotizacionResponse.CotizacionData blueData2 = createMockCotizacionData(200.0, 199.5, 200.5);
        
        CotizacionResponse response1 = new CotizacionResponse();
        response1.setOficial(oficialData1);
        response1.setBlue(blueData1);
        response1.setLastUpdate("2024-01-01T12:00:00Z");
        
        CotizacionResponse response2 = new CotizacionResponse();
        response2.setOficial(oficialData2);
        response2.setBlue(blueData2);
        response2.setLastUpdate("2024-01-01T12:00:00Z");
        
        CotizacionResponse response3 = new CotizacionResponse();
        response3.setOficial(oficialData1);
        response3.setBlue(blueData1);
        response3.setLastUpdate("2024-01-01T13:00:00Z");

        // When & Then
        assertEquals(response1.hashCode(), response2.hashCode(), "Equal objects should have same hashCode");
        assertNotEquals(response1.hashCode(), response3.hashCode(), "Different objects should have different hashCode");
        assertEquals(response1.hashCode(), response1.hashCode(), "Same object should have same hashCode");
    }

    @Test
    @DisplayName("Should test canEqual method")
    void canEqual_ShouldWorkCorrectly() {
        // Given
        CotizacionResponse response1 = new CotizacionResponse();
        CotizacionResponse response2 = new CotizacionResponse();
        CotizacionResponse.CotizacionData data = new CotizacionResponse.CotizacionData();

        // When & Then
        assertTrue(response1.canEqual(response2), "CotizacionResponse should be able to equal another CotizacionResponse");
        assertFalse(response1.canEqual(data), "CotizacionResponse should not be able to equal CotizacionData");
        assertFalse(response1.canEqual(null), "CotizacionResponse should not be able to equal null");
        assertFalse(response1.canEqual("string"), "CotizacionResponse should not be able to equal string");
    }

    @Test
    @DisplayName("Should handle all fields in equality test")
    void equals_WithAllFields_ShouldWorkCorrectly() {
        // Given
        CotizacionResponse.CotizacionData oficialData = createMockCotizacionData(100.0, 99.5, 100.5);
        CotizacionResponse.CotizacionData blueData = createMockCotizacionData(200.0, 199.5, 200.5);
        CotizacionResponse.CotizacionData oficialEuroData = createMockCotizacionData(110.0, 109.5, 110.5);
        CotizacionResponse.CotizacionData blueEuroData = createMockCotizacionData(220.0, 219.5, 220.5);
        
        CotizacionResponse response1 = new CotizacionResponse();
        response1.setOficial(oficialData);
        response1.setBlue(blueData);
        response1.setOficialEuro(oficialEuroData);
        response1.setBlueEuro(blueEuroData);
        response1.setLastUpdate("2024-01-01T12:00:00Z");
        
        CotizacionResponse response2 = new CotizacionResponse();
        response2.setOficial(oficialData);
        response2.setBlue(blueData);
        response2.setOficialEuro(oficialEuroData);
        response2.setBlueEuro(blueEuroData);
        response2.setLastUpdate("2024-01-01T12:00:00Z");

        // When & Then
        assertEquals(response1, response2, "Objects with all fields set should be equal");
        assertEquals(response1.hashCode(), response2.hashCode(), "Objects with all fields set should have same hashCode");
    }

    @Test
    @DisplayName("Should handle null fields in equality test")
    void equals_WithNullFields_ShouldWorkCorrectly() {
        // Given
        CotizacionResponse response1 = new CotizacionResponse();
        CotizacionResponse response2 = new CotizacionResponse();
        
        CotizacionResponse response3 = new CotizacionResponse();
        response3.setOficial(createMockCotizacionData(100.0, 99.5, 100.5));

        // When & Then
        assertEquals(response1, response2, "Objects with null fields should be equal");
        assertNotEquals(response1, response3, "Objects with different null fields should not be equal");
        assertEquals(response1.hashCode(), response2.hashCode(), "Objects with null fields should have same hashCode");
    }

    @Test
    @DisplayName("Should handle CotizacionData equality")
    void cotizacionData_Equals_ShouldWorkCorrectly() {
        // Given
        CotizacionResponse.CotizacionData data1 = createMockCotizacionData(100.0, 99.5, 100.5);
        CotizacionResponse.CotizacionData data2 = createMockCotizacionData(100.0, 99.5, 100.5);
        CotizacionResponse.CotizacionData data3 = createMockCotizacionData(200.0, 199.5, 200.5);

        // When & Then
        assertEquals(data1, data2, "Equal CotizacionData objects should be equal");
        assertNotEquals(data1, data3, "Different CotizacionData objects should not be equal");
        assertEquals(data1, data1, "Same CotizacionData object should be equal to itself");
        assertNotEquals(data1, null, "CotizacionData should not equal null");
        assertNotEquals(data1, "string", "CotizacionData should not equal string");
    }

    @Test
    @DisplayName("Should handle CotizacionData hashCode")
    void cotizacionData_HashCode_ShouldWorkCorrectly() {
        // Given
        CotizacionResponse.CotizacionData data1 = createMockCotizacionData(100.0, 99.5, 100.5);
        CotizacionResponse.CotizacionData data2 = createMockCotizacionData(100.0, 99.5, 100.5);
        CotizacionResponse.CotizacionData data3 = createMockCotizacionData(200.0, 199.5, 200.5);

        // When & Then
        assertEquals(data1.hashCode(), data2.hashCode(), "Equal CotizacionData objects should have same hashCode");
        assertNotEquals(data1.hashCode(), data3.hashCode(), "Different CotizacionData objects should have different hashCode");
        assertEquals(data1.hashCode(), data1.hashCode(), "Same CotizacionData object should have same hashCode");
    }

    @Test
    @DisplayName("Should handle CotizacionData canEqual")
    void cotizacionData_CanEqual_ShouldWorkCorrectly() {
        // Given
        CotizacionResponse.CotizacionData data1 = new CotizacionResponse.CotizacionData();
        CotizacionResponse.CotizacionData data2 = new CotizacionResponse.CotizacionData();
        CotizacionResponse response = new CotizacionResponse();

        // When & Then
        assertTrue(data1.canEqual(data2), "CotizacionData should be able to equal another CotizacionData");
        assertFalse(data1.canEqual(response), "CotizacionData should not be able to equal CotizacionResponse");
        assertFalse(data1.canEqual(null), "CotizacionData should not be able to equal null");
        assertFalse(data1.canEqual("string"), "CotizacionData should not be able to equal string");
    }

    @Test
    @DisplayName("Should handle CotizacionData with null values")
    void cotizacionData_WithNullValues_ShouldWorkCorrectly() {
        // Given
        CotizacionResponse.CotizacionData data1 = new CotizacionResponse.CotizacionData();
        CotizacionResponse.CotizacionData data2 = new CotizacionResponse.CotizacionData();
        
        CotizacionResponse.CotizacionData data3 = new CotizacionResponse.CotizacionData();
        data3.setValueAvg(100.0);

        // When & Then
        assertEquals(data1, data2, "CotizacionData objects with null values should be equal");
        assertNotEquals(data1, data3, "CotizacionData objects with different null values should not be equal");
        assertEquals(data1.hashCode(), data2.hashCode(), "CotizacionData objects with null values should have same hashCode");
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
