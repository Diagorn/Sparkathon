package com.diagorn.sparkathon.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

/**
 * Common error response
 *
 * @param message   - exception message
 * @param timestamp - date-time when error occurred
 * @param status    - returned HTTP status
 * @param error     - error which caused exception status
 * @param path      - request path
 * @author diagorn
 */
public record ErrorResponse(
        String message,
        Instant timestamp,
        int status,
        String error,
        String path
) {
    public static ErrorResponse build(SparkathonException e, WebRequest request) {
        return new ErrorResponse(
                e.getMessage(),
                Instant.now(),
                e.getStatusCode(),
                e.getCause() == null ? null : e.getCause().getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
    }

    public static ErrorResponse build(Exception e, WebRequest request) {
        return new ErrorResponse(
                e.getMessage(),
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getCause() == null ? null : e.getCause().getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
    }
}