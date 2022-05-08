package com.sgenne.timetracking.security.accessToken;

import com.sgenne.timetracking.error.exception.InvalidAccessTokenException;
import com.sgenne.timetracking.security.validation.AccessTokenValidator;
import lombok.Data;

@Data
public class AccessToken {
    private final String token;

    public static AccessToken fromBearerFormat(String bearerToken) {
        String bearerPrefix = "Bearer ";

        AccessTokenValidator
                .bearerTokenFormatIsValid(bearerToken)
                .orThrow(InvalidAccessTokenException::new);

        String tokenString = bearerToken.substring(bearerPrefix.length());
        return new AccessToken(tokenString);
    }
}
