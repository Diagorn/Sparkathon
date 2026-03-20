package com.diagorn.sparkathon.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends SparkathonException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED.value());
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause, HttpStatus.UNAUTHORIZED.value());
    }
}
