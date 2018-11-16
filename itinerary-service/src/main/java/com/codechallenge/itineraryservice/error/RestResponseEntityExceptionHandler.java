package com.codechallenge.itineraryservice.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternalConnect(final RuntimeException ex, final WebRequest request) {
        logger.error("Unknown error", ex);
        final ApiError bodyOfResponse = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error.", ex.getMessage());
        return new ResponseEntity<>(bodyOfResponse, bodyOfResponse.getStatus());

    }

}
