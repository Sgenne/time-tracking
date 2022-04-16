package com.sgenne.timetracking.project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateActivityRequest {
    private String title;
    private String description;
    private String startDateTime;
    private Double duration;
    private Long projectId;
}
