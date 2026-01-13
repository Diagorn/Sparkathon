package com.diagorn.sparkathon.common.exception;

import lombok.Getter;

/**
 * Base exception with status
 *
 * @author diagorn
 */
@Getter
public class SparkathonException extends RuntimeException {
    /**
     * Status code to return for client
     */
    private final int statusCode;
    /**
     * Error description
     */
    private final String description;

    public SparkathonException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.description = message;
    }

    public SparkathonException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
        this.description = message;
    }

}
