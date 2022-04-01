package com.sgenne.timetracking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;

public class JWTServices {

    private final static int JWT_DURATION = 1000 * 60 * 60;

    public static String createAccessToken(User user) {

        Algorithm algorithm = createJWTAlgorithm();

        return JWT
                .create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_DURATION))
                .withClaim("roles", user
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .sign(algorithm);
    }

    public static Algorithm createJWTAlgorithm() {
        return Algorithm.HMAC256("A super secret secret".getBytes());
    }



}
