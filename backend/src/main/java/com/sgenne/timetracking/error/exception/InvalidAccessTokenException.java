package com.sgenne.timetracking.error.exception;

public class InvalidAccessTokenException extends AppException {
    public static final Integer INVALID_ACCESS_TOKEN_STATUS = 403;
    public InvalidAccessTokenException(String message) {
        super(INVALID_ACCESS_TOKEN_STATUS, message);
    }
}
