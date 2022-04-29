package com.sgenne.timetracking.error;

import lombok.Data;

@Data
public class ExceptionResponse {
    private final String message;

    public ExceptionResponse(RuntimeException exception) {
        this.message = exception.getMessage();
    }
}
