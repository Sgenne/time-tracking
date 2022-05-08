package com.sgenne.timetracking.security.accessToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class AccessTokenUtils implements Serializable {
    private static final long JWT_TOKEN_DURATION = 60L * 60L * 1000L;
    @Setter
    @Value("${jwt.secret}")
    private String secret;

    public String parseUsernameFromAuthenticationToken(AccessToken token) {
        String tokenString = token.getToken();

        Claims claims = Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(tokenString)
                .getBody();

        return claims.getSubject();
    }

    public AccessToken generateToken(UserDetails userDetails) {
        String token =  Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_DURATION))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return new AccessToken(token);
    }
}
