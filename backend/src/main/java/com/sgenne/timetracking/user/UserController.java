package com.sgenne.timetracking.user;

import com.sgenne.timetracking.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(UserController.USER_ROOT_URL)
public class UserController {
    public static final String USER_ROOT_URL = "/api/v1/user";
    private final UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}
