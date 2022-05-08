package com.sgenne.timetracking.security.accessToken;

import com.sgenne.timetracking.security.user.AppUserDetails;
import com.sgenne.timetracking.user.model.Role;
import com.sgenne.timetracking.user.model.User;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenUtilsTest {

    @Test
    void parseUsernameFromAuthenticationToken_validToken() {
        String username = "username";
        int duration = 100000;
        String secret = "secret";

        AccessToken accessToken = createAccessToken(username, secret, duration);

        AccessTokenUtils accessTokenUtils = new AccessTokenUtils();
        accessTokenUtils.setSecret(secret);

        String parsedUsername = accessTokenUtils
                .parseUsernameFromAuthenticationToken(accessToken);

        assertEquals(username, parsedUsername);
    }

    @Test
    void parseUsernameFromAuthenticationToken_validToken_expired() {
        String username = "username";
        int duration = -1;
        String secret = "secret";

        AccessToken accessToken = createAccessToken(username, secret, duration);

        AccessTokenUtils accessTokenUtils = new AccessTokenUtils();
        accessTokenUtils.setSecret(secret);

        assertThrows(ExpiredJwtException.class, () -> {
            accessTokenUtils.parseUsernameFromAuthenticationToken(accessToken);
        });
    }

    @Test
    void parseUsernameFromAuthenticationToken_invalidToken() {
        String username = "username";
        int duration = 100000;
        String secret = "secret";

        AccessToken accessToken = createAccessToken(username, secret, duration);

        char[] tokenChars = accessToken.getToken().toCharArray();

        tokenChars[0] = (char) (tokenChars[0] + 1);

        AccessToken invalidAccessToken = new AccessToken(new String(tokenChars));

        AccessTokenUtils accessTokenUtils = new AccessTokenUtils();
        accessTokenUtils.setSecret(secret);

        assertThrows(JwtException.class, () -> {
            accessTokenUtils.parseUsernameFromAuthenticationToken(invalidAccessToken);
        });
    }

    @Test
    void generateToken() {
        String username = "username";
        String password = "password";
        List<Role> roles = List.of(Role.USER);
        String secret = "secret";

        UserDetails userDetails = new AppUserDetails(new User(username, password, roles));

        AccessTokenUtils accessTokenUtils = new AccessTokenUtils();
        accessTokenUtils.setSecret(secret);

        AccessToken accessToken = accessTokenUtils.generateToken(userDetails);

        Claims claims = Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(accessToken.getToken())
                .getBody();

        assertEquals(claims.getSubject(), username);
    }

    private AccessToken createAccessToken(String username, String secret, long duration) {
        String tokenString =  Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return new AccessToken(tokenString);
    }


}