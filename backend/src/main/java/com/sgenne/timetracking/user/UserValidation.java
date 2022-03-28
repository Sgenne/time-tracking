package com.sgenne.timetracking.user;

import com.sgenne.timetracking.validation.Validator;

public class UserValidation {
    /**
     * Checks that the given value is a valid username.If the value is
     * not a valid username, then an IllegalArgumentException is thrown.
     */
    public static void usernameIsValid(String username) {
        Validator.hasMinLength(username, 3)
                .orThrow(() -> new IllegalArgumentException("The username must be at least 3 characters long."));
        Validator.hasMaxLength(username, 16)
                .orThrow(() -> new IllegalArgumentException("The username must not be more than 16 characters long."));
    }

    /**
     * Checks that the given value is a valid email. If the value is not
     * a valid email, then an illegalArgumentException is thrown.
     */
    public static void emailIsValid(String email) {
        Validator.isEmail(email)
                .orThrow(() -> new IllegalArgumentException("The given email was invalid."));
    }

    /**
     * Checks that the given value is a valid password. If the value is not
     * a valid password, then an IllegalArumentException is thrown.
     */
    public static void passwordIsValid(String password) {
        Validator.hasMinLength(password, 5)
                .orThrow(() -> new IllegalArgumentException("The password must be at least 5 characters long."));
    }

}
