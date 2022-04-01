package com.sgenne.timetracking.security.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserRequest {
    private final String username;
    private final String password;
    private final String email;
}
