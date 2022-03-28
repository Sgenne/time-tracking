package com.sgenne.timetracking.authentication;

import com.sgenne.timetracking.user.User;
import com.sgenne.timetracking.user.UserController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.sgenne.timetracking.api.API.URL_PREFIX;

@RequestMapping(AuthenticationController.ROOT_URL)
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService registrationService;

    public static final String ROOT_URL = URL_PREFIX + "/auth";

    @PostMapping("/")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        User user = registrationService.registerUser(request);
        URI uri = URI
                .create(ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(UserController.ROOT_URL + "/" + user.getId())
                        .toUriString());
        return ResponseEntity.created(uri).build();
    }
}
