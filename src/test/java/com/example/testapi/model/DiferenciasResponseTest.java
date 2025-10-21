package com.example.testapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DiferenciasResponse model.
 * This class contains comprehensive tests for the DiferenciasResponse data model,
 * including the static factory method and all getter/setter functionality.
 */
@DisplayName("DiferenciasResponse Tests")
class DiferenciasResponseTest {

    private DiferenciasResponse diferenciasResponse;

    @BeforeEach
    void setUp() {
        diferenciasResponse = new DiferenciasResponse(310.0, 315.0, 305.0);
    }

    @Test
    @DisplayName("Should create DiferenciasResponse with default constructor")
    void constructor_Default_ShouldCreateEmptyResponse() {
        // When
        DiferenciasResponse response = new DiferenciasResponse();

        // Then
        assertNotNull(response, "Response should not be null");
        assertNull(response.getDiferenciaAvg(), "DiferenciaAvg should be null initially");
        assertNull(response.getDiferenciaSell(), "DiferenciaSell should be null initially");
        assertNull(response.getDiferenciaBuy(), "DiferenciaBuy should be null initially");
    }

    @Test
    @DisplayName("Should create DiferenciasResponse with all parameters constructor")
    void constructor_WithAllParameters_ShouldCreateResponseWithValues() {
        // Given
        Double avg = 100.0;
        Double sell = 105.0;
        Double buy = 95.0;

        // When
        DiferenciasResponse response = new DiferenciasResponse(avg, sell, buy);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(avg, response.getDiferenciaAvg(), "DiferenciaAvg should match");
        assertEquals(sell, response.getDiferenciaSell(), "DiferenciaSell should match");
        assertEquals(buy, response.getDiferenciaBuy(), "DiferenciaBuy should match");
    }

    @Test
    @DisplayName("Should create DiferenciasResponse using static factory method")
    void of_StaticFactoryMethod_ShouldCreateResponseCorrectly() {
        // Given
        Double avg = 200.0;
        Double sell = 210.0;
        Double buy = 190.0;

        // When
        DiferenciasResponse response = DiferenciasResponse.of(avg, sell, buy);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(avg, response.getDiferenciaAvg(), "DiferenciaAvg should match");
        assertEquals(sell, response.getDiferenciaSell(), "DiferenciaSell should match");
        assertEquals(buy, response.getDiferenciaBuy(), "DiferenciaBuy should match");
    }

    @Test
    @DisplayName("Should set and get diferenciaAvg correctly")
    void setDiferenciaAvg_ShouldSetDiferenciaAvgCorrectly() {
        // Given
        Double expectedAvg = 500.0;

        // When
        diferenciasResponse.setDiferenciaAvg(expectedAvg);

        // Then
        assertEquals(expectedAvg, diferenciasResponse.getDiferenciaAvg(), "DiferenciaAvg should be set correctly");
    }

    @Test
    @DisplayName("Should set and get diferenciaSell correctly")
    void setDiferenciaSell_ShouldSetDiferenciaSellCorrectly() {
        // Given
        Double expectedSell = 600.0;

        // When
        diferenciasResponse.setDiferenciaSell(expectedSell);

        // Then
        assertEquals(expectedSell, diferenciasResponse.getDiferenciaSell(), "DiferenciaSell should be set correctly");
    }

    @Test
    @DisplayName("Should set and get diferenciaBuy correctly")
    void setDiferenciaBuy_ShouldSetDiferenciaBuyCorrectly() {
        // Given
        Double expectedBuy = 700.0;

        // When
        diferenciasResponse.setDiferenciaBuy(expectedBuy);

        // Then
        assertEquals(expectedBuy, diferenciasResponse.getDiferenciaBuy(), "DiferenciaBuy should be set correctly");
    }

    @Test
    @DisplayName("Should handle null values")
    void setValues_WithNull_ShouldHandleNullValues() {
        // When
        diferenciasResponse.setDiferenciaAvg(null);
        diferenciasResponse.setDiferenciaSell(null);
        diferenciasResponse.setDiferenciaBuy(null);

        // Then
        assertNull(diferenciasResponse.getDiferenciaAvg(), "DiferenciaAvg should be null");
        assertNull(diferenciasResponse.getDiferenciaSell(), "DiferenciaSell should be null");
        assertNull(diferenciasResponse.getDiferenciaBuy(), "DiferenciaBuy should be null");
    }

    @Test
    @DisplayName("Should handle zero values")
    void setValues_WithZero_ShouldHandleZeroValues() {
        // When
        diferenciasResponse.setDiferenciaAvg(0.0);
        diferenciasResponse.setDiferenciaSell(0.0);
        diferenciasResponse.setDiferenciaBuy(0.0);

        // Then
        assertEquals(0.0, diferenciasResponse.getDiferenciaAvg(), "DiferenciaAvg should be zero");
        assertEquals(0.0, diferenciasResponse.getDiferenciaSell(), "DiferenciaSell should be zero");
        assertEquals(0.0, diferenciasResponse.getDiferenciaBuy(), "DiferenciaBuy should be zero");
    }

    @Test
    @DisplayName("Should handle negative values")
    void setValues_WithNegative_ShouldHandleNegativeValues() {
        // When
        diferenciasResponse.setDiferenciaAvg(-100.0);
        diferenciasResponse.setDiferenciaSell(-200.0);
        diferenciasResponse.setDiferenciaBuy(-300.0);

        // Then
        assertEquals(-100.0, diferenciasResponse.getDiferenciaAvg(), "DiferenciaAvg should be negative");
        assertEquals(-200.0, diferenciasResponse.getDiferenciaSell(), "DiferenciaSell should be negative");
        assertEquals(-300.0, diferenciasResponse.getDiferenciaBuy(), "DiferenciaBuy should be negative");
    }

    @Test
    @DisplayName("Should return correct string representation")
    void toString_ShouldReturnCorrectStringRepresentation() {
        // When
        String result = diferenciasResponse.toString();

        // Then
        assertNotNull(result, "String representation should not be null");
        assertTrue(result.contains("DiferenciasResponse"), "Should contain class name");
        assertTrue(result.contains("310.0"), "Should contain diferenciaAvg value");
        assertTrue(result.contains("315.0"), "Should contain diferenciaSell value");
        assertTrue(result.contains("305.0"), "Should contain diferenciaBuy value");
    }

    @Test
    @DisplayName("Should test equality between DiferenciasResponse objects")
    void equality_ShouldWorkCorrectly() {
        // Given
        DiferenciasResponse response1 = new DiferenciasResponse(100.0, 105.0, 95.0);
        DiferenciasResponse response2 = new DiferenciasResponse(100.0, 105.0, 95.0);
        DiferenciasResponse response3 = new DiferenciasResponse(200.0, 205.0, 195.0);

        // When & Then
        assertEquals(response1, response2, "Equal response objects should be equal");
        assertNotEquals(response1, response3, "Different response objects should not be equal");
        assertEquals(response1.hashCode(), response2.hashCode(), "Equal objects should have same hash code");
    }

    @Test
    @DisplayName("Should test static factory method with null values")
    void of_WithNullValues_ShouldHandleNullValues() {
        // When
        DiferenciasResponse response = DiferenciasResponse.of(null, null, null);

        // Then
        assertNotNull(response, "Response should not be null");
        assertNull(response.getDiferenciaAvg(), "DiferenciaAvg should be null");
        assertNull(response.getDiferenciaSell(), "DiferenciaSell should be null");
        assertNull(response.getDiferenciaBuy(), "DiferenciaBuy should be null");
    }
}
