package com.sgenne.timetracking.error;

import com.sgenne.timetracking.error.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(ResourceNotFoundException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(new ExceptionResponse(exception));
    }
}
