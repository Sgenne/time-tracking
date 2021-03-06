package com.sgenne.timetracking.security.controller;

import com.sgenne.timetracking.error.exception.InvalidCredentialsException;
import com.sgenne.timetracking.security.request.AuthenticationRequest;
import com.sgenne.timetracking.security.accessToken.AccessToken;
import com.sgenne.timetracking.security.accessToken.AccessTokenUtils;
import com.sgenne.timetracking.security.user.AppUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sgenne.timetracking.api.APIConstants.APPLICATION_JSON;

@RestController
@AllArgsConstructor
@RequestMapping(AuthenticationController.AUTHENTICATION_ROOT_URL)
public class AuthenticationController {
    public static final String AUTHENTICATION_ROOT_URL = "/api/v1/authenticate";

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final AccessTokenUtils accessTokenUtils;

    /**
     * Authenticates a user and produces an AuthenticationToken.
     * @param request Contains the user credentials to authenticate.
     * @return A response with the newly created AuthenticationToken.
     */
    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<AccessToken> authenticateUser(@RequestBody AuthenticationRequest request) {
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

        AccessToken accessToken = accessTokenUtils.generateToken(userDetails);


        return ResponseEntity.ok(accessToken);
    }
}
