package com.sgenne.timetracking.project.service;

import com.sgenne.timetracking.project.repository.ActivityRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActivityServiceTest {

    @Test
    void createActivity() {
        ActivityRepository mockActivityRepository = mock(ActivityRepository.class);
        ProjectService mockProjectService = mock(ProjectService.class);

/*
        when(mockActivityRepository.save())
*/

        ActivityService activityService = new ActivityService(mockActivityRepository, mockProjectService);

        assert false;
    }

    @Test
    void getActivityById() {
        assert false;
    }
}