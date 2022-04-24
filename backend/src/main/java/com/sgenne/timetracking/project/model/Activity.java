package com.sgenne.timetracking.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * An activity performed as part of a project.
 */
@Data
@Entity
@Table
@NoArgsConstructor
public class Activity {

    @SequenceGenerator(name = "activity_sequence", sequenceName = "activity_sequence")
    @GeneratedValue(generator = "activity_sequence")
    @Id
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;

    private Long projectId;

    /**
     * The duration of the activity in minutes.
     */
    private Double duration;

    public Activity(String title, String description, LocalDateTime startDateTime, Long projectId, Double duration) {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.projectId = projectId;
        this.duration = duration;
    }
}
