package com.sgenne.timetracking.project.controller;

import com.sgenne.timetracking.project.model.Activity;
import com.sgenne.timetracking.project.model.Project;
import com.sgenne.timetracking.project.request.CreateActivityRequest;
import com.sgenne.timetracking.project.service.ActivityService;
import lombok.AllArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.sgenne.timetracking.api.APIConstants.APPLICATION_JSON;

@RestController
@AllArgsConstructor
@RequestMapping(ActivityController.ACTIVITY_ROOT_URL)
public class ActivityController {
    public static final String ACTIVITY_ROOT_URL = "/api/v1/activity";
    private final ActivityService activityService;

    /**
     * Adds an Activity to a specified project.
     * @param request The contents of the Activity.
     * @return A ResponseEntity with the uri of the added Activity and an empty body.
     */
    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<Void> addActivity(@RequestBody CreateActivityRequest request) {
        Activity createdActivity = activityService.createActivity(request);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(ACTIVITY_ROOT_URL + "/" + createdActivity.getId())
                .toUriString());

        return ResponseEntity.created(uri).build();
    }

    /**
     * Gets an Activity.
     * @param activityId The id of the Activity.
     * @return A ResponseEntity with the found Activity.
     */
    @GetMapping(value = "/{activityId}", produces = APPLICATION_JSON)
    public ResponseEntity<Activity> getActivityById(@PathVariable Long activityId) {
        Activity activity = activityService.getActivityById(activityId);

        return ResponseEntity.ok(activity);
    }

    /**
     * Gets all activities of a specified project.
     * @param projectId The id of the project.
     * @return A ResponseEntity with the activities of the project.
     */
    @GetMapping(value = "/by-project/{projectId}", produces = APPLICATION_JSON)
    public ResponseEntity<List<Activity>> getActivityByProjectId(@PathVariable Long projectId) {
        List<Activity> activities = activityService.getActivityByProjectId(projectId);

        return ResponseEntity.ok().body(activities);
    }



}
