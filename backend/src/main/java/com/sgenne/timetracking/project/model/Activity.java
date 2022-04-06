package com.sgenne.timetracking.project.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Data
public class Activity {

    @SequenceGenerator(name = "activity_sequence", sequenceName = "activity_sequence")
    @GeneratedValue(generator = "activity_sequence")
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private Double duration;

    public Activity(String title, String description, LocalDateTime startDateTime, Double duration) {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.duration = duration;
    }
}
