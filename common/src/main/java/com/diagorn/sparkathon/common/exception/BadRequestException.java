package com.diagorn.sparkathon.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends SparkathonException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST.value());
    }
}
