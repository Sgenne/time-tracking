package com.sgenne.timetracking.security.jwt;

import lombok.Data;

@Data
public class AuthenticationToken {
    private final String token;

    public static AuthenticationToken fromBearerFormat(String bearerString) {
        String bearerPrefix = "Bearer ";
        String tokenString = bearerString.substring(bearerPrefix.length());
        return new AuthenticationToken(tokenString);
    }
}
