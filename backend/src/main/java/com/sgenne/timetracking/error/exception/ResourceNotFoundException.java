package com.sgenne.timetracking.error.exception;

import lombok.Data;

public class ResourceNotFoundException extends AppException {
    public final static Integer RESOURCE_NOT_FOUND_STATUS = 404;

    public ResourceNotFoundException(String message) {
        super(RESOURCE_NOT_FOUND_STATUS, message);
    }
}
