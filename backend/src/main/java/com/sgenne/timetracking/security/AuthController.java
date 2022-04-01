package com.sgenne.timetracking.security;

import com.sgenne.timetracking.security.request.RegisterUserRequest;
import com.sgenne.timetracking.user.User;
import com.sgenne.timetracking.user.UserController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.sgenne.timetracking.api.API.URL_PREFIX;

@RestController
@RequestMapping(AuthController.BASE_URL)
@AllArgsConstructor
public class AuthController {

    public static final String BASE_URL = URL_PREFIX + "/auth";
    public static final String REGISTER_USER_URL = BASE_URL + "/register";

    private final AuthService authService;

    @PostMapping(REGISTER_USER_URL)
    public ResponseEntity<Void> registerUser(RegisterUserRequest request) {
        User user = authService.registerUser(request);

        URI uri = URI
                .create(ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(UserController.ROOT_URL + "/" + user.getId())
                        .toUriString());
        return ResponseEntity.created(uri).build();
    }




}
