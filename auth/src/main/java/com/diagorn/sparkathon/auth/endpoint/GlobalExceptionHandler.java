package com.diagorn.sparkathon.auth.endpoint;

import com.diagorn.sparkathon.auth.dto.ErrorResponse;
import com.diagorn.sparkathon.auth.exception.BadRequestException;
import com.diagorn.sparkathon.auth.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

import static org.springframework.http.HttpStatus.*;

/**
 * Global REST API exception handling
 *
 * @author diagorn
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, WebRequest request) {
        log.error("Status 400: bad request occured", ex);
        final HttpStatus status = BAD_REQUEST;
        ErrorResponse errorResponse = build(ex, status, request);
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, WebRequest request) {
        log.error("Status 404: not found occured", ex);
        final HttpStatus status = NOT_FOUND;
        ErrorResponse errorResponse = build(ex, status, request);
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownError(Exception ex, WebRequest request) {
        log.error("Status 500: unknown error occured", ex);
        final HttpStatus status = INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = build(ex, status, request);
        return ResponseEntity.status(status).body(errorResponse);
    }

    private ErrorResponse build(Exception e, HttpStatus httpStatus, WebRequest request) {
        return ErrorResponse.builder()
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(e.getMessage())
                .timestamp(Instant.now())
                .path(getPath(request))
                .build();
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
