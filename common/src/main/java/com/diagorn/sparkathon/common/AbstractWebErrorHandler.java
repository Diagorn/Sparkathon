package com.diagorn.sparkathon.common;

import com.diagorn.sparkathon.common.exception.ErrorResponse;
import com.diagorn.sparkathon.common.exception.SparkathonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Abstract handler for all errors of the application
 *
 * @author diagorn
 */
@Slf4j
public abstract class AbstractWebErrorHandler {

    @ExceptionHandler(SparkathonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(SparkathonException ex, WebRequest webRequest) {
        log.error(
                String.format(
                        "Exception %s occured: responding with status %d",
                        ex.getClass().getSimpleName(),
                        ex.getStatusCode()
                )
        );
        logStacktrace(ex);

        var errorResponse = ErrorResponse.build(ex, webRequest);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex, WebRequest webRequest) {
        log.error(
                String.format(
                        "Exception %s occured: responding with status %d",
                        ex.getClass().getSimpleName(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                )
        );
        logStacktrace(ex);

        var errorResponse = ErrorResponse.build(ex, webRequest);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    /**
     * Log stacktrace into database
     * Override this if you want this to be saved for later analysis
     * @param ex - caught exception
     */
    protected void logStacktrace(Exception ex) {}
}
