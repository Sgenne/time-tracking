package com.sgenne.timetracking.security.controller;

import com.sgenne.timetracking.error.exception.InvalidCredentialsException;
import com.sgenne.timetracking.security.jwt.JwtRequest;
import com.sgenne.timetracking.security.jwt.JwtResponse;
import com.sgenne.timetracking.security.jwt.JwtTokenUtil;
import com.sgenne.timetracking.security.user.AppUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sgenne.timetracking.api.APIConstants.APPLICATION_JSON;

@RestController
@AllArgsConstructor
@RequestMapping(AuthenticationController.AUTHENTICATION_ROOT_URL)
public class AuthenticationController {
    public static final String AUTHENTICATION_ROOT_URL = "/api/v1/authentication";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AppUserDetailsService userDetailsService;

    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<JwtResponse> generateAuthenticationToken(@RequestBody JwtRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        authenticate(username, password);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);
        try{
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            throw InvalidCredentialsException.forUsername(username);
        }
    }

}
