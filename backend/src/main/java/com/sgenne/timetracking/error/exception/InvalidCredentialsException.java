package com.sgenne.timetracking.error.exception;

public class InvalidCredentialsException extends AppException{

    public static final Integer INVALID_USER_CREDENTIALS_STATUS = 401;

    public InvalidCredentialsException(String message) {
        super(INVALID_USER_CREDENTIALS_STATUS, message);
    }
}
