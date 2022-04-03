package com.sgenne.timetracking.project.model;


import lombok.AllArgsConstructor;
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

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
