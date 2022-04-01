package com.sgenne.timetracking.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static com.sgenne.timetracking.security.JWTServices.createJWTAlgorithm;
import static com.sgenne.timetracking.security.SecurityConfig.LOGIN_URL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(LOGIN_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String authorizationHeaderPrefix = "Bearer ";

        if (!(authorizationHeader != null && authorizationHeader.startsWith(authorizationHeaderPrefix))) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorizationHeader.substring(authorizationHeaderPrefix.length());
        Algorithm algorithm = createJWTAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT;

        try {
            decodedJWT = verifier.verify(accessToken);
        }catch (JWTVerificationException exception) {
            response.setHeader("error", exception.getMessage());
            response.setStatus(FORBIDDEN.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), Map.of("error", exception.getMessage()));
            return;
        }

        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = Arrays
                .stream(roles)
                .map(SimpleGrantedAuthority::new)
                .toList();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder
                .getContext()
                .setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
