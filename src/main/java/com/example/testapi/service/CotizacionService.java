package com.example.testapi.service;

import com.example.testapi.model.CotizacionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * Service class for handling cotizacion (quotation) related business logic.
 * This service encapsulates the business logic for quotation operations,
 * including external API calls to retrieve exchange rate data.
 */
@Service
public class CotizacionService {

    private static final Logger logger = LoggerFactory.getLogger(CotizacionService.class);
    @Value("${BLUELYTICS_API_URL:https://api.bluelytics.com.ar/v2/latest}")
    private String bluelyticsApiUrl;

    private final WebClient webClient;

    public void setBluelyticsApiUrl(String bluelyticsApiUrl) {
        this.bluelyticsApiUrl = bluelyticsApiUrl;
    }

    /**
     * Constructor for CotizacionService.
     *
     * @param webClientBuilder the WebClient.Builder instance for making HTTP requests
     */
    public CotizacionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    /**
     * Retrieves the latest exchange rate data from the external API.
     * This method makes a GET request to the Bluelytics API and returns
     * the response data.
     *
     * @return CotizacionResponse containing the exchange rate data
     * @throws RuntimeException if the external API call fails
     */
    public CotizacionResponse obtenerCotizacion() {
        try {
            logger.info("Initiating request to external API: {}", bluelyticsApiUrl);
            
            CotizacionResponse response = webClient
                    .get()
                    .uri(bluelyticsApiUrl)
                    .retrieve()
                    .bodyToMono(CotizacionResponse.class)
                    .block();

            logger.info("Successfully retrieved exchange rate data");
            return response;

        } catch (WebClientResponseException e) {
            logger.error("Error calling external API. Status: {}, Response: {}", 
                        e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Error retrieving exchange rate data from external API", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while calling external API", e);
            throw new RuntimeException("Unexpected error occurred while retrieving exchange rate data", e);
        }
    }
}
