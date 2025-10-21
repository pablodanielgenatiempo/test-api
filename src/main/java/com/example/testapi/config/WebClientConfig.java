package com.example.testapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for WebClient and other application configurations.
 * This class provides bean definitions for external API communication.
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a WebClient bean for making HTTP requests to external APIs.
     * This WebClient is configured with default settings and can be used
     * throughout the application for external API calls.
     *
     * @return WebClient.Builder instance
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
