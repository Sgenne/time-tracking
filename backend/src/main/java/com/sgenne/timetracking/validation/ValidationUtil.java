package com.sgenne.timetracking.validation;

public class ValidationUtil {

    /**
     * Returns true if the given value only contains alphanumeric characters.
     * @param value The String to validate.
     */
    public static boolean isAlphaNumeric(String value) {
           return value.chars().allMatch(Character::isLetterOrDigit);
    }
}
