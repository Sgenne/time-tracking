package com.sgenne.timetracking.error.exception;

public class BadRequestException extends AppException{
    public static final Integer BAD_REQUEST_STATUS = 400;

    public BadRequestException(String message) {
        super(BAD_REQUEST_STATUS, message);
    }
}
