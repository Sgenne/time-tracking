package com.sgenne.timetracking.project.request;

import lombok.Data;

@Data
public class CreateProjectRequest {
    private final String title;
    private final String description;
    private final Long ownerId;
}
