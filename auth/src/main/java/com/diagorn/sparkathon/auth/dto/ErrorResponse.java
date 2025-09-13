package com.diagorn.sparkathon.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * HTTP response for errors
 *
 * @author diagorn
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    /**
     * Exception message
     */
    private String message;
    /**
     * Time when exception occured
     */
    private Instant timestamp;
    /**
     * HTTP status code
     */
    private int status;
    /**
     * Error which caused exception status
     */
    private String error;
    /**
     * Request path
     */
    private String path;
}
