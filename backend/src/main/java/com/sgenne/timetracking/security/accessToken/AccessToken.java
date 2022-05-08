package com.sgenne.timetracking.security.accessToken;

import lombok.Data;

@Data
public class AccessToken {
    private final String token;

    public static AccessToken fromBearerFormat(String bearerString) {
        String bearerPrefix = "Bearer ";
        String tokenString = bearerString.substring(bearerPrefix.length());
        return new AccessToken(tokenString);
    }
}
