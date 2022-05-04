package com.sgenne.timetracking.error.exception;

public abstract class AppException extends RuntimeException {

    private final Integer statusCode;
    private final String message;

    protected AppException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    };
    public String getMessage() {
        return message;
    }
}
