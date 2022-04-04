package com.sgenne.timetracking.user;

import com.sgenne.timetracking.project.model.Project;
import com.sgenne.timetracking.project.request.CreateProjectRequest;
import com.sgenne.timetracking.project.service.ProjectService;
import com.sgenne.timetracking.user.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.sgenne.timetracking.user.model.Role.ADMIN;
import static com.sgenne.timetracking.user.model.Role.USER;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserService userService, ProjectService projectService) {
        return args -> {
            User user = new User("sgenne", "123123", List.of(USER, ADMIN));
            userService.saveUser(user);
            System.out.println("hello");
        };
    }

}
