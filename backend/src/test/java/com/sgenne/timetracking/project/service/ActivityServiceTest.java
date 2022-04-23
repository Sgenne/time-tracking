package com.sgenne.timetracking.project.service;

import com.sgenne.timetracking.project.model.Activity;
import com.sgenne.timetracking.project.model.Project;
import com.sgenne.timetracking.project.repository.ActivityRepository;
import com.sgenne.timetracking.project.repository.ProjectRepository;
import com.sgenne.timetracking.project.request.CreateActivityRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActivityServiceTest {

    @Test
    void createActivity() {
        ActivityRepository mockActivityRepository = mock(ActivityRepository.class);

        ProjectRepository mockProjectRepository = mock(ProjectRepository.class);

        String activityTitle = "title";
        String activityDescription = "description";
        String activityStartDateTime = LocalDateTime.now().toString();
        Double activityDuration = 60.;
        Long projectId = 1L;

        CreateActivityRequest createActivityRequest =
                new CreateActivityRequest(
                        activityTitle,
                        activityDescription,
                        activityStartDateTime,
                        activityDuration,
                        projectId);

        Activity newActivity = new Activity();
        Long activityId = 1L;
        newActivity.setId(activityId);

        when(mockActivityRepository
                .save(any(Activity.class)))
                .thenReturn(newActivity);

        when(mockProjectRepository.findById(activityId)).thenReturn(Optional.of(new Project()));

        ActivityService activityService = new ActivityService(mockActivityRepository, mockProjectRepository);

        Activity resultActivity = activityService.createActivity(createActivityRequest);

        assert resultActivity.getTitle().equals(activityTitle);
    }

    @Test
    void getActivityById() {

        ActivityRepository mockActivityRepository = mock(ActivityRepository.class);

        ProjectRepository mockProjectRepository = mock(ProjectRepository.class);


        Long activityId = 1L;
        Activity existingActivity = new Activity("title",
                "description",
                LocalDateTime.now(),
                new Project(),
                60.);
        existingActivity.setId(activityId);

        when(mockActivityRepository
                .findById(activityId))
                .thenReturn(Optional.of(existingActivity));

        ActivityService activityService =
                new ActivityService(mockActivityRepository, mockProjectRepository);

        Activity foundActivity = activityService.getActivityById(activityId);

        assert foundActivity.equals(existingActivity);
    }
}