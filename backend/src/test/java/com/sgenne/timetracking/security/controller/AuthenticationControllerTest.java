package com.sgenne.timetracking.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgenne.timetracking.security.accessToken.AccessTokenUtils;
import com.sgenne.timetracking.security.request.AuthenticationRequest;
import com.sgenne.timetracking.security.user.AppUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AuthenticationController authenticationController;

    @MockBean private AuthenticationManager authenticationManager;
    @MockBean private UserDetailsService userDetailsService;
    @MockBean private AccessTokenUtils accessTokenUtils;

    @Test
    @WithMockUser
    void authenticateUser() throws Exception {

        String username = "username";
        String password = "password";
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(username, password);

        when(authenticationManager.authenticate(any()))
                .thenReturn(usernamePasswordAuthenticationToken);

        MockHttpServletResponse response
                = mockMvc.perform(MockMvcRequestBuilders
                        .post(AuthenticationController.AUTHENTICATION_ROOT_URL)
                        .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());

    }
}