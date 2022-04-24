package com.sgenne.timetracking.project.service;

import com.sgenne.timetracking.project.model.Activity;
import com.sgenne.timetracking.project.model.Project;
import com.sgenne.timetracking.project.repository.ActivityRepository;
import com.sgenne.timetracking.project.repository.ProjectRepository;
import com.sgenne.timetracking.project.request.CreateActivityRequest;
import lombok.AllArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static com.sgenne.timetracking.project.sanitizer.ActivitySanitizer.sanitizeDescription;
import static com.sgenne.timetracking.project.sanitizer.ActivitySanitizer.sanitizeTitle;
import static com.sgenne.timetracking.project.validation.ActivityValidator.durationIsValid;
import static com.sgenne.timetracking.project.validation.ActivityValidator.startDateTimeStringIsValid;
import static com.sgenne.timetracking.project.validation.ProjectValidator.descriptionIsValid;
import static com.sgenne.timetracking.project.validation.ProjectValidator.titleIsValid;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final ProjectRepository projectRepository;

    /**
     * Adds an activity to a project.
     * @param request The request containing the activity information and the id of the
     *                project in which the activity was performed.
     * @return The project in which the activity was performed.
     */
    public Activity createActivity(CreateActivityRequest request) {
        String title = sanitizeTitle(request.getTitle());
        String description = sanitizeDescription(request.getDescription());
        String startTime = request.getStartDateTime();
        Double duration = request.getDuration();
        Long projectId = request.getProjectId();

        titleIsValid(title)
                .orThrow((message) -> new ResponseStatusException(BAD_REQUEST, message));
        descriptionIsValid(description)
                .orThrow((message) -> new ResponseStatusException(BAD_REQUEST, message));
        durationIsValid(duration)
                .orThrow((message) -> new ResponseStatusException(BAD_REQUEST, message));
        startDateTimeStringIsValid(startTime)
                .orThrow((message) -> new ResponseStatusException(BAD_REQUEST, message));

        LocalDateTime startDateTime = LocalDateTime.parse(startTime);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        String.format("No project with the id \"%s\" was found", projectId)));

        Activity newActivity = new Activity(title, description, startDateTime, project.getId(), duration);

        activityRepository.save(newActivity);

        return newActivity;
    }

    public Activity getActivityById(Long activityId) {
        return activityRepository
                .findById(activityId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        String.format("No activity with id \"%s\" was found.", activityId)));
    }

    /**
     * Gets the recorded Activities of the specified project.
     * @param projectId The id of the project.
     * @return The found Activities.
     */
    public List<Activity> getActivityByProjectId(Long projectId) {

        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        String.format("No project with the id \"%s\" was found.", projectId)));

        return activityRepository.getActivitiesByProjectId(projectId);
    }
}
