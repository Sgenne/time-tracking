package com.sgenne.timetracking.error.exception;

public class InvalidUserCredentialsException extends AppException{

    public static final Integer INVALID_USER_CREDENTIALS_STATUS = 401;

    public InvalidUserCredentialsException(String message) {
        super(INVALID_USER_CREDENTIALS_STATUS, message);
    }
}
