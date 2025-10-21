package com.example.testapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for the diferencias endpoint.
 * This class represents the calculated differences between MEP and crypto exchange rate values.
 * Contains the differences for average, sell, and buy values.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiferenciasResponse {

    /**
     * The difference between MEP average value and crypto average value.
     * Positive values indicate MEP is higher than crypto, negative values indicate the opposite.
     */
    @JsonProperty("diferencia_avg")
    private Double diferenciaAvg;

    /**
     * The difference between MEP sell value and crypto sell value.
     * Positive values indicate MEP sell rate is higher than crypto sell rate.
     */
    @JsonProperty("diferencia_sell")
    private Double diferenciaSell;

    /**
     * The difference between MEP buy value and crypto buy value.
     * Positive values indicate MEP buy rate is higher than crypto buy rate.
     */
    @JsonProperty("diferencia_buy")
    private Double diferenciaBuy;

    /**
     * Creates a DiferenciasResponse with calculated differences.
     * 
     * @param diferenciaAvg the calculated difference for average values
     * @param diferenciaSell the calculated difference for sell values
     * @param diferenciaBuy the calculated difference for buy values
     * @return a new DiferenciasResponse instance with the provided differences
     */
    public static DiferenciasResponse of(Double diferenciaAvg, Double diferenciaSell, Double diferenciaBuy) {
        return new DiferenciasResponse(diferenciaAvg, diferenciaSell, diferenciaBuy);
    }
}
