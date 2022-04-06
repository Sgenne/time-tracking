package com.sgenne.timetracking.project.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddActivityRequest {
    private final String title;
    private final String description;
    private final String startDateTime;
    private final Double duration;
    private final Long projectId;
}
