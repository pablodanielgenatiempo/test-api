package com.example.testapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a cotizacion (quotation) response.
 * This class encapsulates the response data for quotation-related endpoints.
 */
@Data
@NoArgsConstructor
public class CotizacionResponse {

    @JsonProperty("oficial")
    private CotizacionData oficial;

    @JsonProperty("blue")
    private CotizacionData blue;

    @JsonProperty("oficial_euro")
    private CotizacionData oficialEuro;

    @JsonProperty("blue_euro")
    private CotizacionData blueEuro;

    @JsonProperty("last_update")
    private String lastUpdate;

    /**
     * Inner class representing exchange rate data.
     */
    @Data
    @NoArgsConstructor
    public static class CotizacionData {
        @JsonProperty("value_avg")
        private Double valueAvg;

        @JsonProperty("value_sell")
        private Double valueSell;

        @JsonProperty("value_buy")
        private Double valueBuy;
    }
}
