package com.sgenne.timetracking.user;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import static com.sgenne.timetracking.api.API.URL_PREFIX;



@RequestMapping(UserController.ROOT_URL)
@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String ROOT_URL = URL_PREFIX + "/user";

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }
}
