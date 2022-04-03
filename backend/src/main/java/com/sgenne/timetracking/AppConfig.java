package com.sgenne.timetracking;

import com.sgenne.timetracking.user.User;
import com.sgenne.timetracking.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            User user = new User("sgenne", "123123");
            userService.saveUser(user);
        };
    }



}
