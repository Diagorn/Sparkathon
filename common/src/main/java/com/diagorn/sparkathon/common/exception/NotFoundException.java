package com.diagorn.sparkathon.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends SparkathonException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND.value());
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND.value());
    }
}
