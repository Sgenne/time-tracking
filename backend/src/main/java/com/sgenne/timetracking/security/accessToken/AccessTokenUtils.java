package com.sgenne.timetracking.security.accessToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    public String getUsernameFromToken(AccessToken token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(AccessToken token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    private <T> T getClaimFromToken(AccessToken token, Function<Claims, T> claimsResolver) {
        String tokenString = token.getToken();

        Claims claims = Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(tokenString)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_DURATION))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean tokenIsExpired(AccessToken token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
