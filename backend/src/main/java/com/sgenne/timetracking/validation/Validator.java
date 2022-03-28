package com.sgenne.timetracking.validation;

import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

@AllArgsConstructor
public class Validator {
    /**
     * Checks that the given string is a valid email.
     */
    public static ValidationResult isEmail(String value) {
        String emailRegex = "^(.+)@(.+)$";

        boolean emailIsValid = Pattern
                .compile(emailRegex)
                .matcher(value)
                .matches();

        return new ValidationResult(emailIsValid);
    }

    /**
     * Checks that the given string does not have fewer characters
     * than the specified minLength.
     * @param value The value to be validated.
     * @param minLength The minimum valid length of the value.
     */
    public static ValidationResult hasMinLength(String value, int minLength) {
        boolean isValid = value.length() >= minLength;
        return new ValidationResult(isValid);
    }

    /**
     * Checks that the given string does not have more characters
     * than the specified maxLength.
     * @param value The value to be validated.
     * @param maxLength The maximum valid length of the value.
     */
    public static ValidationResult hasMaxLength(String value, int maxLength) {
        boolean isValid = value.length() <= maxLength;
        return new ValidationResult(isValid);
    }
}
