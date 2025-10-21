package com.example.testapi.controller;

import com.example.testapi.model.CotizacionResponse;
import com.example.testapi.service.CotizacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for handling cotizacion (quotation) related HTTP requests.
 * This controller exposes endpoints for quotation operations and delegates
 * business logic to the service layer.
 */
@RestController
@RequestMapping("/api/v1")
public class CotizacionController {

    private static final Logger logger = LoggerFactory.getLogger(CotizacionController.class);

    private final CotizacionService cotizacionService;

    /**
     * Constructor for CotizacionController.
     *
     * @param cotizacionService the service for handling cotizacion business logic
     */
    public CotizacionController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    /**
     * GET endpoint to retrieve exchange rate quotation.
     * This endpoint makes an internal request to an external API and returns
     * the exchange rate data to the client.
     *
     * @return ResponseEntity containing the exchange rate data
     */
    @GetMapping("/cotizacion")
    public ResponseEntity<CotizacionResponse> obtenerCotizacion() {
        logger.info("Received request to retrieve exchange rate quotation");
        
        try {
            CotizacionResponse response = cotizacionService.obtenerCotizacion();
            logger.info("Exchange rate quotation retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Error retrieving exchange rate quotation", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
