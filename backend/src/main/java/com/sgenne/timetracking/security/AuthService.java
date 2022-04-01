package com.sgenne.timetracking.security;

import com.sgenne.timetracking.security.request.RegisterUserRequest;
import com.sgenne.timetracking.user.User;
import com.sgenne.timetracking.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public User registerUser(RegisterUserRequest request) {
        String username = request.getUsername();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String email = request.getEmail();

        return userService.saveUser(new User(username, encodedPassword, email));
    }
}
