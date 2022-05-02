package com.sgenne.timetracking.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

public class JwtTokenUtil implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }





    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

    }


}
