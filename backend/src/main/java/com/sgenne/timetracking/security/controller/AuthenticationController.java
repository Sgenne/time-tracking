package com.sgenne.timetracking.security.controller;

import com.sgenne.timetracking.security.jwt.JwtTokenUtil;
import com.sgenne.timetracking.security.user.AppUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AppUserDetailsService userDetailsService;


}
