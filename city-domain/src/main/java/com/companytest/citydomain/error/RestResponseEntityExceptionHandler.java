package com.companytest.citydomain.error;

import com.mongodb.MongoException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    @ExceptionHandler({ MongoException.class })
    public ResponseEntity<Object> handleInternalMongo(final RuntimeException ex, final WebRequest request) {
        logger.error("Mongo error - 500 Status Code", ex);
        final ApiError bodyOfResponse = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "There was an issue with mongo database.", ex.getMessage());
        return new ResponseEntity<>(bodyOfResponse, bodyOfResponse.getStatus());

    }

    @ExceptionHandler({ ConnectException.class })
    public ResponseEntity<Object> handleInternalConnect(final RuntimeException ex, final WebRequest request) {
        logger.error("Mongo error - 500 Status Code", ex);
        final ApiError bodyOfResponse = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "There was an issue connecting with an external service.", ex.getMessage());
        return new ResponseEntity<>(bodyOfResponse, bodyOfResponse.getStatus());

    }

}
