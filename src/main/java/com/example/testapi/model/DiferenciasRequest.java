package com.example.testapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumMap;
import java.util.Map;

/**
 * Request DTO for the /api/v1/diferencias endpoint using EnumMap.
 * This approach provides type safety, better performance, and flexibility for different exchange rate types.
 * 
 * JSON Structure:
 * {
 *   "rates": {
 *     "crypto": { "value_avg": 940.0, "value_sell": 945.0, "value_buy": 935.0 },
 *     "mep": { "value_avg": 1250.0, "value_sell": 1260.0, "value_buy": 1240.0 }
 *   }
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiferenciasRequest {

    @JsonProperty("rates")
    private Map<ExchangeRateType, ExchangeRateData> rates;

    /**
     * Enum for exchange rate types.
     * Provides type safety and compile-time validation.
     */
    public enum ExchangeRateType {
        @JsonProperty("crypto")
        CRYPTO,
        @JsonProperty("mep")
        MEP
    }

    /**
     * Inner class representing exchange rate data structure.
     * Contains the three standard exchange rate values: average, sell, and buy.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeRateData {
        
        /**
         * The average exchange rate value.
         * Represents the mean value between buy and sell rates.
         */
        @JsonProperty("value_avg")
        private Double valueAvg;

        /**
         * The sell exchange rate value.
         * Represents the rate at which the currency can be sold.
         */
        @JsonProperty("value_sell")
        private Double valueSell;

        /**
         * The buy exchange rate value.
         * Represents the rate at which the currency can be bought.
         */
        @JsonProperty("value_buy")
        private Double valueBuy;
    }

    /**
     * Convenience method to create rates map.
     * Returns an EnumMap for optimal performance.
     * 
     * @return new EnumMap instance
     */
    public static Map<ExchangeRateType, ExchangeRateData> createRatesMap() {
        return new EnumMap<>(ExchangeRateType.class);
    }

    /**
     * Builder pattern for easier construction.
     * Provides a fluent API for creating DiferenciasRequest instances.
     */
    public static class Builder {
        private Map<ExchangeRateType, ExchangeRateData> rates = createRatesMap();

        public Builder addRate(ExchangeRateType type, ExchangeRateData data) {
            rates.put(type, data);
            return this;
        }

        public Builder addCrypto(Double avg, Double sell, Double buy) {
            return addRate(ExchangeRateType.CRYPTO, new ExchangeRateData(avg, sell, buy));
        }

        public Builder addMep(Double avg, Double sell, Double buy) {
            return addRate(ExchangeRateType.MEP, new ExchangeRateData(avg, sell, buy));
        }

        public DiferenciasRequest build() {
            return new DiferenciasRequest(rates);
        }
    }

    /**
     * Creates a new Builder instance.
     * 
     * @return new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
}