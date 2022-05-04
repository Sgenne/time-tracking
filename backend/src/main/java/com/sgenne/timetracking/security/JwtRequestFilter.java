package com.sgenne.timetracking.security;

import com.sgenne.timetracking.error.exception.BadRequestException;
import com.sgenne.timetracking.error.exception.InvalidCredentialsException;
import com.sgenne.timetracking.security.jwt.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        String bearerPrefix = "Bearer ";

        if (tokenHeader == null || !tokenHeader.startsWith(bearerPrefix)) {
            throw new BadRequestException("The request did not contain a valid access token.");
        }

        String username;
        String token = tokenHeader.substring(bearerPrefix.length());

        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new BadRequestException("The request did not contain a valid access token.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        boolean tokenIsValid = jwtTokenUtil.validateToken(token, userDetails);

        if (!tokenIsValid) {
            throw new InvalidCredentialsException("The given access token was invalid.");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        userDetails.getPassword(),
                        userDetails.getAuthorities());

        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
