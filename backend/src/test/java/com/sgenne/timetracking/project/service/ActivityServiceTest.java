package com.sgenne.timetracking.project.service;

import com.sgenne.timetracking.project.model.Activity;
import com.sgenne.timetracking.project.repository.ActivityRepository;
import com.sgenne.timetracking.project.request.CreateActivityRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActivityServiceTest {

    @MockBean
    private ActivityRepository mockActivityRepository;

    @MockBean
    private ProjectService mockProjectService;


    @Test
    void createActivity() {
        String activityTitle = "title";
        String activityDescription = "description";
        String activityStartDateTime = LocalDateTime.now().toString();
        Double activityDuration = 60.;
        Long projectId = 1L;

        CreateActivityRequest createActivityRequest = new CreateActivityRequest(activityTitle, activityDescription, activityStartDateTime, activityDuration, projectId);
        Activity newActivity = new Activity();
        Long activityId = 1L;
        newActivity.setId(activityId);

        when(mockActivityRepository
                .save(newActivity))
                .thenReturn(newActivity);

        ActivityService activityService = new ActivityService(mockActivityRepository, mockProjectService);

        Activity resultActivity = activityService.createActivity(newActivity);

        assert ;
    }

    @Test
    void getActivityById() {
        assert false;
    }
}