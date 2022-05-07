package com.sgenne.timetracking.security.controller;

import com.sgenne.timetracking.error.exception.InvalidCredentialsException;
import com.sgenne.timetracking.security.jwt.AuthenticationRequest;
import com.sgenne.timetracking.security.jwt.AuthenticationToken;
import com.sgenne.timetracking.security.jwt.AccessTokenUtils;
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
    private final AccessTokenUtils accessTokenUtils;
    private final AppUserDetailsService userDetailsService;

    /**
     * Authenticates a user and produces an AuthenticationToken.
     * @param request Contains the user credentials to authenticate.
     * @return A response with the newly created AuthenticationToken.
     */
    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<AuthenticationToken> authenticateUser(@RequestBody AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        UsernamePasswordAuthenticationToken usernamePasswordToken =
                new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(usernamePasswordToken);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("The given credentials were invalid.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        AuthenticationToken authenticationToken =
                new AuthenticationToken(
                        accessTokenUtils.generateToken(userDetails)
                );

        return ResponseEntity.ok(authenticationToken);
    }
}
