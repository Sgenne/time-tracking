package com.sgenne.timetracking;

import com.sgenne.timetracking.user.model.User;
import com.sgenne.timetracking.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.sgenne.timetracking.user.model.Role.ADMIN;
import static com.sgenne.timetracking.user.model.Role.USER;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            User user = new User("sgenne", "123123", List.of(USER, ADMIN));
            userService.saveUser(user);
        };
    }

}
