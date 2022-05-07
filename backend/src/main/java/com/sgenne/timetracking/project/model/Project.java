package com.sgenne.timetracking.project.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class Project {

    @Id
    @SequenceGenerator(name = "project_sequence", sequenceName = "project_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_sequence")
    private Long id;
    private String title;
    private String description;
    private Long ownerId;

    public Project(String title, String description, Long ownerId) {
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
    }
}
