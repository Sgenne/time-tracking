package com.sgenne.timetracking.project.service;

import com.sgenne.timetracking.project.model.Project;
import com.sgenne.timetracking.project.repository.ProjectRepository;
import com.sgenne.timetracking.project.request.CreateProjectRequest;
import com.sgenne.timetracking.user.UserRepository;
import com.sgenne.timetracking.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    private static User createOwner() {
        Long ownerId = 1L;
        User owner = new User();
        owner.setId(ownerId);

        return owner;
    }

    private static CreateProjectRequest createCreateProjectRequest(Long ownerId) {
        String projectTitle = "projectTitle";
        String projectDescription = "projectDescription";

        return new CreateProjectRequest(
                projectTitle,
                projectDescription,
                ownerId);
    }


    @Test
    void createProject_userExists() {
        ProjectRepository mockProjectRepository = mock(ProjectRepository.class);
        UserRepository mockUserRepository = mock(UserRepository.class);

        User owner = createOwner();

        when(mockUserRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(mockProjectRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        ProjectService projectService = new ProjectService(mockProjectRepository, mockUserRepository);

        CreateProjectRequest createProjectRequest = createCreateProjectRequest(owner.getId());

        Project result = projectService.createProject(createProjectRequest);

        assertEquals(result.getOwnerId(), owner.getId());
        assertEquals(result.getTitle(), createProjectRequest.getTitle());
        assertEquals(result.getDescription(), createProjectRequest.getDescription());
    }

    @Test
    void createProject_userDoesNotExist() {
        ProjectRepository mockProjectRepository = mock(ProjectRepository.class);
        UserRepository mockUserRepository = mock(UserRepository.class);

        Long ownerId = 1L;

        when(mockUserRepository.findById(ownerId)).thenReturn(Optional.ofNullable(null));
        when(mockProjectRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        ProjectService projectService = new ProjectService(mockProjectRepository, mockUserRepository);

        CreateProjectRequest createProjectRequest = createCreateProjectRequest(ownerId);

        assertThrows(ResponseStatusException.class, () -> {
            projectService.createProject(createProjectRequest);
        });
    }


    @Test
    void getProjectById_projectFound() {
        ProjectRepository mockProjectRepository = mock(ProjectRepository.class);
        UserRepository mockUserRepository = mock(UserRepository.class);

        String projectTitle = "projectTitle";
        String projectDescription = "projectDescription";
        Long projectId = 1L;

        Project project = new Project(projectTitle, projectDescription, projectId);

        when(mockProjectRepository.findById(projectId)).thenReturn(Optional.of(project));

        ProjectService projectService =
                new ProjectService(mockProjectRepository, mockUserRepository);

        Project result = projectService.getProjectById(projectId);

        assertEquals(result, project);
    }

    @Test
    void getProjectById_projectNotFound() {
        ProjectRepository mockProjectRepository = mock(ProjectRepository.class);
        UserRepository mockUserRepository = mock(UserRepository.class);

        Long projectId = 1L;

        when(mockProjectRepository.findById(projectId)).thenReturn(Optional.ofNullable(null));

        ProjectService projectService =
                new ProjectService(mockProjectRepository, mockUserRepository);

        assertThrows(ResponseStatusException.class, () -> {
            projectService.getProjectById(projectId);
        } );
    }
}