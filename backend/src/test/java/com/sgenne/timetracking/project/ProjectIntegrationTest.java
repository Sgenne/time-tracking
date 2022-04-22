package com.sgenne.timetracking.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgenne.timetracking.project.request.CreateProjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.sgenne.timetracking.project.controller.ProjectController.PROJECT_ROOT_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProject() throws Exception{
        Long ownerId = 1L;
        String title = "Title";
        String description = "Description";

        CreateProjectRequest request =
                new CreateProjectRequest(title, description, ownerId);

        mockMvc.perform(post(PROJECT_ROOT_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }


}
