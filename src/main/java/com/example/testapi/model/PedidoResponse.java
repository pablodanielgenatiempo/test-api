package com.example.testapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a pedido (order) response.
 * This class encapsulates the response data for order-related endpoints.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {

    @JsonProperty("mensaje")
    private String mensaje;
}
