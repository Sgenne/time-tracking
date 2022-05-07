package com.sgenne.timetracking.error;

import com.sgenne.timetracking.error.exception.AppException;
import com.sgenne.timetracking.error.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionResponse> handleException(AppException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(new ExceptionResponse(exception));
    }
}
