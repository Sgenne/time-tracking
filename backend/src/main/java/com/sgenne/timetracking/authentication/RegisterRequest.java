package com.sgenne.timetracking.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterRequest {
    private final String username;
    private final String password;
    private final String email;
}
