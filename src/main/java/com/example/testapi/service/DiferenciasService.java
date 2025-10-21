package com.example.testapi.service;

import com.example.testapi.exception.ApiTestException;
import com.example.testapi.model.DiferenciasRequest;
import com.example.testapi.model.DiferenciasResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class for handling diferencias (differences) related business logic.
 * This service encapsulates the business logic for calculating differences between
 * MEP and crypto exchange rate values, including validation for negative differences.
 */
@Service
public class DiferenciasService {

    private static final Logger logger = LoggerFactory.getLogger(DiferenciasService.class);

    /**
     * Calculates the differences between MEP and crypto exchange rate values.
     * This method computes the differences for average, sell, and buy values,
     * and throws an ApiTestException if any difference is negative.
     * 
     * @param request the DiferenciasRequest containing exchange rate data in EnumMap format
     * @return DiferenciasResponse containing the calculated differences
     * @throws ApiTestException if any calculated difference is negative
     * @throws IllegalArgumentException if the request or its data is null or invalid
     */
    public DiferenciasResponse calcularDiferencias(DiferenciasRequest request) {
        logger.info("Starting calculation of differences between MEP and crypto values");
        
        // Validate input
        validateRequest(request);
        
        // Extract values using EnumMap for type safety
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates = request.getRates();
        DiferenciasRequest.ExchangeRateData crypto = rates.get(DiferenciasRequest.ExchangeRateType.CRYPTO);
        DiferenciasRequest.ExchangeRateData mep = rates.get(DiferenciasRequest.ExchangeRateType.MEP);
        
        // Calculate differences (MEP - Crypto)
        Double diferenciaAvg = mep.getValueAvg() - crypto.getValueAvg();
        Double diferenciaSell = mep.getValueSell() - crypto.getValueSell();
        Double diferenciaBuy = mep.getValueBuy() - crypto.getValueBuy();
        
        logger.debug("Calculated differences - Avg: {}, Sell: {}, Buy: {}", 
                    diferenciaAvg, diferenciaSell, diferenciaBuy);
        
        // Check for negative differences and collect them
        List<String> negativeItems = new ArrayList<>();
        
        if (diferenciaAvg < 0) {
            negativeItems.add("avg");
        }
        if (diferenciaSell < 0) {
            negativeItems.add("sell");
        }
        if (diferenciaBuy < 0) {
            negativeItems.add("buy");
        }
        
        // Throw exception if any difference is negative
        if (!negativeItems.isEmpty()) {
            String errorMessage = String.format("Negative differences found for items: %s", 
                                              String.join(", ", negativeItems));
            logger.error("Negative differences detected: {}", errorMessage);
            throw new ApiTestException(errorMessage);
        }
        
        logger.info("Successfully calculated differences - Avg: {}, Sell: {}, Buy: {}", 
                  diferenciaAvg, diferenciaSell, diferenciaBuy);
        
        return DiferenciasResponse.of(diferenciaAvg, diferenciaSell, diferenciaBuy);
    }
    
    /**
     * Validates the input request to ensure it contains valid data.
     * 
     * @param request the DiferenciasRequest to validate
     * @throws IllegalArgumentException if the request or its data is null or invalid
     */
    private void validateRequest(DiferenciasRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        Map<DiferenciasRequest.ExchangeRateType, DiferenciasRequest.ExchangeRateData> rates = request.getRates();
        if (rates == null) {
            throw new IllegalArgumentException("Rates map cannot be null");
        }
        
        if (!rates.containsKey(DiferenciasRequest.ExchangeRateType.CRYPTO)) {
            throw new IllegalArgumentException("Crypto data is required");
        }
        
        if (!rates.containsKey(DiferenciasRequest.ExchangeRateType.MEP)) {
            throw new IllegalArgumentException("MEP data is required");
        }
        
        validateExchangeRateData(rates.get(DiferenciasRequest.ExchangeRateType.CRYPTO), "crypto");
        validateExchangeRateData(rates.get(DiferenciasRequest.ExchangeRateType.MEP), "MEP");
    }
    
    /**
     * Validates exchange rate data to ensure all values are present and valid.
     * 
     * @param data the ExchangeRateData to validate
     * @param dataType the type of data being validated (for error messages)
     * @throws IllegalArgumentException if any value is null or invalid
     */
    private void validateExchangeRateData(DiferenciasRequest.ExchangeRateData data, String dataType) {
        if (data.getValueAvg() == null) {
            throw new IllegalArgumentException(String.format("%s value_avg cannot be null", dataType));
        }
        
        if (data.getValueSell() == null) {
            throw new IllegalArgumentException(String.format("%s value_sell cannot be null", dataType));
        }
        
        if (data.getValueBuy() == null) {
            throw new IllegalArgumentException(String.format("%s value_buy cannot be null", dataType));
        }
        
        // Additional validation: values should be positive
        if (data.getValueAvg() <= 0) {
            throw new IllegalArgumentException(String.format("%s value_avg must be positive", dataType));
        }
        
        if (data.getValueSell() <= 0) {
            throw new IllegalArgumentException(String.format("%s value_sell must be positive", dataType));
        }
        
        if (data.getValueBuy() <= 0) {
            throw new IllegalArgumentException(String.format("%s value_buy must be positive", dataType));
        }
    }
}
