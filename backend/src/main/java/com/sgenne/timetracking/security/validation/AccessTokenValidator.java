package com.sgenne.timetracking.security.validation;

import com.sgenne.timetracking.validation.ValidationResult;

public class AccessTokenValidator {

    private static final String bearerPrefix = "Bearer ";

    /**
     * Validates that the given token has a valid format. The function does not validate that the access token
     * itself is correct.
     * @param bearerToken The token whose format is validated.
     * @return A ValidationResult.
     */
    public static ValidationResult bearerTokenFormatIsValid(String bearerToken) {

        if (bearerToken.length() <= bearerPrefix.length()) {
            return ValidationResult.inValid("The token was too short.");
        }

        if (!bearerToken.startsWith(bearerPrefix)) {
            return ValidationResult.inValid("The token did not start with \"Bearer \".");
        }

        return ValidationResult.valid();
    }
}
