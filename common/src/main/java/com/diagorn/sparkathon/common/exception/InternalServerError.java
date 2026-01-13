package com.diagorn.sparkathon.common.exception;

import org.springframework.http.HttpStatus;

public class InternalServerError extends SparkathonException {
    public InternalServerError(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public InternalServerError(String message, Throwable cause, int statusCode) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
