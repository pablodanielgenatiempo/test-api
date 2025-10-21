package com.example.testapi.controller;

import com.example.testapi.model.PedidoResponse;
import com.example.testapi.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for handling pedido (order) related HTTP requests.
 * This controller exposes endpoints for order operations and delegates
 * business logic to the service layer.
 */
@RestController
@RequestMapping("/api/v1")
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    private final PedidoService pedidoService;

    /**
     * Constructor for PedidoController.
     *
     * @param pedidoService the service for handling pedido business logic
     */
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * GET endpoint to process an order.
     * This endpoint returns a JSON response indicating that the order was processed.
     *
     * @return ResponseEntity containing the order processing message
     */
    @GetMapping("/pedido")
    public ResponseEntity<PedidoResponse> procesarPedido() {
        logger.info("Received request to process order");
        
        try {
            PedidoResponse response = pedidoService.procesarPedido();
            logger.info("Order processed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing order", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
