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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException exception) {
        System.out.println("exception: " + exception.getMessage());
        return ResponseEntity.status(500).body(new ExceptionResponse(exception));
    }

}
