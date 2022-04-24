package com.sgenne.timetracking.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgenne.timetracking.project.model.Project;
import com.sgenne.timetracking.project.request.CreateProjectRequest;
import com.sgenne.timetracking.project.service.ProjectService;
import com.sgenne.timetracking.user.model.Role;
import com.sgenne.timetracking.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.sgenne.timetracking.project.controller.ProjectController.PROJECT_ROOT_URL;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    @Test
    public void getProjectById() throws Exception {
        Long projectId = 1L;

        Project existingProject =
                new Project("Title",
                        "description",
                        1L);
        existingProject.setId(projectId);

        when(projectService.getProjectById(projectId))
                .thenReturn(existingProject);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(PROJECT_ROOT_URL + "/" + projectId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.title")
                        .value(existingProject.getTitle()));
    }

    @Test
    void createProject() throws Exception {
        Long projectId = 1L;
        CreateProjectRequest request = new CreateProjectRequest("title", "description", 0L);
        Project createdProject = new Project();
        createdProject.setId(projectId);

        when(projectService.createProject(request)).thenReturn(createdProject);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post(PROJECT_ROOT_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String locationHeaderValue = mvcResult
                .getResponse()
                .getHeader("Location");

        assert locationHeaderValue.matches("^.+" + PROJECT_ROOT_URL + "/" + projectId);
    }
}