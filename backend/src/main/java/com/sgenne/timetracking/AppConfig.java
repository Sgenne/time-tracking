package com.sgenne.timetracking;

import com.sgenne.timetracking.project.request.CreateActivityRequest;
import com.sgenne.timetracking.project.request.CreateProjectRequest;
import com.sgenne.timetracking.project.service.ActivityService;
import com.sgenne.timetracking.project.service.ProjectService;
import com.sgenne.timetracking.user.UserService;
import com.sgenne.timetracking.user.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.sgenne.timetracking.user.model.Role.ADMIN;
import static com.sgenne.timetracking.user.model.Role.USER;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserService userService, ProjectService projectService, ActivityService activityService) {
        return args -> {
            User user = new User("sgenne", "123123", List.of(USER, ADMIN));
            userService.saveUser(user);

            CreateProjectRequest createProjectRequest =
                    new CreateProjectRequest("Title", "Description", 1L);
            projectService.createProject(createProjectRequest);

            CreateActivityRequest createActivityRequest =
                    new CreateActivityRequest(
                            "ActivityTitle",
                            "ActivityDescription",
                            "2022-04-15T17:03:28",
                            60.,
                            1L);

            CreateActivityRequest createActivityRequest1 =
                    new CreateActivityRequest(
                            "ActivityTitle1",
                            "ActivityDescription1",
                            "2022-04-16T17:03:28",
                            30.,
                            1L);

            activityService.createActivity(createActivityRequest);
            activityService.createActivity(createActivityRequest1);
        };
    }

}
