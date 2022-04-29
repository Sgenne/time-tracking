package com.sgenne.timetracking.error.exception;

import lombok.Data;

public class ResourceNotFoundException extends RuntimeException {
    private final String message;
    public final static Integer RESOURCE_NOT_FOUND_STATUS = 404;

    public ResourceNotFoundException(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return RESOURCE_NOT_FOUND_STATUS;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
