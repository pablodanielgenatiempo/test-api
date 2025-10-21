package com.example.testapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error response DTO for API error responses.
 * This class encapsulates error information returned by the API endpoints.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private String timestamp;

    /**
     * Creates an ErrorResponse with the given error message.
     *
     * @param message the error message
     * @return ErrorResponse instance
     */
    public static ErrorResponse of(String message) {
        return new ErrorResponse("Bad Request", message, java.time.Instant.now().toString());
    }

    /**
     * Creates an ErrorResponse with custom error type and message.
     *
     * @param error the error type
     * @param message the error message
     * @return ErrorResponse instance
     */
    public static ErrorResponse of(String error, String message) {
        return new ErrorResponse(error, message, java.time.Instant.now().toString());
    }
}
