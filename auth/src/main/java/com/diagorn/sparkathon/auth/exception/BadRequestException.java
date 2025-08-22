package com.diagorn.sparkathon.auth.exception;

/**
 * Exception for 400 Bad Request HTTP status
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
