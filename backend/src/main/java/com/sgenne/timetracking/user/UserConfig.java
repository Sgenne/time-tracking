package com.sgenne.timetracking.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return (args -> {
            User sgenne = userService.saveUser(new User("Sgenne", "password", "simon.genne@gmail.com"));
            userService.saveUser(new User("SamHam", "password", "samham@test.com"));
            userService.saveUser(new User("HamSam", "password", "hamsam@test.com"));

            userService.updateUserRole(sgenne.getId(), Role.ADMIN);
        });
    }
}
