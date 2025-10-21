package com.example.testapi.service;

import com.example.testapi.model.PedidoResponse;
import org.springframework.stereotype.Service;

/**
 * Service class for handling pedido (order) related business logic.
 * This service encapsulates the business logic for order operations.
 */
@Service
public class PedidoService {

    /**
     * Processes an order and returns a response message.
     * This method handles the business logic for order processing.
     *
     * @return PedidoResponse containing the processing message
     */
    public PedidoResponse procesarPedido() {
        // Business logic for order processing would go here
        // For now, we return a simple success message
        return new PedidoResponse("El pedido fue procesado");
    }
}
