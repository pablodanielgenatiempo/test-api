package com.example.testapi.controller;

import com.example.testapi.exception.ApiTestException;
import com.example.testapi.model.DiferenciasRequest;
import com.example.testapi.model.DiferenciasResponse;
import com.example.testapi.model.ErrorResponse;
import com.example.testapi.service.DiferenciasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * REST Controller for handling diferencias (differences) related HTTP requests.
 * This controller exposes endpoints for calculating differences between MEP and crypto
 * exchange rate values and delegates business logic to the service layer.
 */
@RestController
@RequestMapping("/api/v1")
public class DiferenciasController {

    private static final Logger logger = LoggerFactory.getLogger(DiferenciasController.class);

    private final DiferenciasService diferenciasService;

    /**
     * Constructor for DiferenciasController.
     *
     * @param diferenciasService the service for handling diferencias business logic
     */
    public DiferenciasController(DiferenciasService diferenciasService) {
        this.diferenciasService = diferenciasService;
    }

    /**
     * POST endpoint to calculate differences between MEP and crypto exchange rate values.
     * This endpoint receives a JSON request containing crypto and MEP exchange rate data,
     * calculates the differences (MEP - Crypto), and returns the results.
     * 
     * If any calculated difference is negative, an ApiTestException is thrown with
     * details about which specific items caused the negative differences.
     *
     * @param request the DiferenciasRequest containing crypto and MEP exchange rate data
     * @return ResponseEntity containing the calculated differences or error information
     */
    @PostMapping("/diferencias")
    public ResponseEntity<?> calcularDiferencias(@Valid @RequestBody DiferenciasRequest request) {
        logger.info("Received request to calculate differences between MEP and crypto values");
        
        try {
            DiferenciasResponse response = diferenciasService.calcularDiferencias(request);
            logger.info("Differences calculated successfully");
            return ResponseEntity.ok(response);
            
        } catch (ApiTestException e) {
            logger.error("ApiTestException occurred while calculating differences: {}", e.getMessage());
            ErrorResponse errorResponse = ErrorResponse.of("Negative differences detected", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request data: {}", e.getMessage());
            ErrorResponse errorResponse = ErrorResponse.of("Validation error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            
        } catch (Exception e) {
            logger.error("Unexpected error occurred while calculating differences", e);
            ErrorResponse errorResponse = ErrorResponse.of("Internal server error", "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
