package com.sgenne.timetracking.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgenne.timetracking.project.model.Activity;
import com.sgenne.timetracking.project.request.CreateActivityRequest;
import com.sgenne.timetracking.project.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.sgenne.timetracking.project.controller.ActivityController.ACTIVITY_ROOT_URL;
import static com.sgenne.timetracking.project.controller.ProjectController.PROJECT_ROOT_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@WebMvcTest(ActivityController.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ActivityService activityService;

    @Test
    void addActivity() throws Exception {
        Long activityId = 1L;
        CreateActivityRequest request = new CreateActivityRequest();
        Activity createdActivity = new Activity();
        createdActivity.setId(activityId);

        when(activityService.createActivity(request))
                .thenReturn(createdActivity);

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders
                                .post(ACTIVITY_ROOT_URL)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andReturn()
                        .getResponse();

        assert response.getStatus() == 201;
        assert response
                .getHeader("Location")
                .matches("^.+" + ACTIVITY_ROOT_URL + "/" + activityId);

    }

    @Test
    void getActivityById() throws Exception{
        Long activityId = 1L;
        Activity existingActivity = new Activity();
        existingActivity.setId(activityId);

        when(activityService.getActivityById(activityId))
                .thenReturn(existingActivity);

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders
                        .get(ACTIVITY_ROOT_URL + "/" + activityId))
                .andReturn()
                .getResponse();

        assert response.getStatus() == 200;
        assert response.getContentType().equals(APPLICATION_JSON_VALUE);

    }
}