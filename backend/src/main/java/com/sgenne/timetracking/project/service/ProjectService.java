package com.sgenne.timetracking.project.service;

import com.sgenne.timetracking.project.model.Project;
import com.sgenne.timetracking.project.repository.ProjectRepository;
import com.sgenne.timetracking.project.request.CreateProjectRequest;
import com.sgenne.timetracking.user.UserService;
import com.sgenne.timetracking.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.sgenne.timetracking.project.validation.ProjectValidator.descriptionIsValid;
import static com.sgenne.timetracking.project.validation.ProjectValidator.titleIsValid;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    /**
     * Creates and stores a Project.
     * @param request Contains the information that will be used when creating the project.
     * @return The created project.
     */
    public Project createProject(CreateProjectRequest request) {
        String title = request.getTitle();
        String description = request.getDescription();
        Long ownerId = request.getOwnerId();
        User owner = userService.getUserById(ownerId);

        System.out.println("Before validation");

        titleIsValid(title).orThrow(
                (message) -> new ResponseStatusException(BAD_REQUEST, message));

        System.out.println("After first validation");

        descriptionIsValid(description).orThrow(
                (message) -> new ResponseStatusException(BAD_REQUEST, message));


        System.out.println("After validation");


        Project project = new Project(title, description, owner);

        return projectRepository.save(project);
    }


    public Project getProjectById(Long projectId) {
        return this
                .projectRepository
                .findById(projectId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format(
                                        "No project with project-id \"%s\" was found.",
                                        projectId)));
    }




}
