package com.sgenne.timetracking.security;

import com.sgenne.timetracking.error.exception.BadRequestException;
import com.sgenne.timetracking.error.exception.InvalidCredentialsException;
import com.sgenne.timetracking.security.jwt.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

@Component
@AllArgsConstructor
@Transactional
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        String bearerPrefix = "Bearer ";

        if (tokenHeader == null || !tokenHeader.startsWith(bearerPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username;
        String token = tokenHeader.substring(bearerPrefix.length());

        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            System.out.println("HERE");
            System.out.println("Exception: " + e.getClass());
            filterChain.doFilter(request, response);
            return;
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
