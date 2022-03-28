package com.sgenne.timetracking.authentication;

import com.sgenne.timetracking.user.User;
import com.sgenne.timetracking.user.UserService;
import com.sgenne.timetracking.user.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationService {
    private final UserService userService;

    public User registerUser(RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        UserValidation.usernameIsValid(username);
        UserValidation.passwordIsValid(password);
        UserValidation.emailIsValid(email);

        User user = new User(username, password, email);
        userService.saveUser(user);

        return user;
    }


}
