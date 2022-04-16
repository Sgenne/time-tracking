package com.sgenne.timetracking.project.controller;

import com.sgenne.timetracking.project.model.Activity;
import com.sgenne.timetracking.project.model.Project;
import com.sgenne.timetracking.project.request.CreateActivityRequest;
import com.sgenne.timetracking.project.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.sgenne.timetracking.api.APIConstants.APPLICATION_JSON;

@RestController
@AllArgsConstructor
@RequestMapping(ActivityController.ACTIVITY_ROOT_URL)
public class ActivityController {
    public static final String ACTIVITY_ROOT_URL = "/api/v1/activity";
    private final ActivityService activityService;

    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<Void> addActivity(CreateActivityRequest request) {
        Activity createdActivity = activityService.createActivity(request);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(ACTIVITY_ROOT_URL + createdActivity.getId())
                .toUriString());


        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{activityId}", produces = APPLICATION_JSON)
    public ResponseEntity<Activity> getActivityById(@PathVariable Long activityId) {
        Activity activity = activityService.getActivityById(activityId);

        return ResponseEntity.ok(activity);
    }
}
