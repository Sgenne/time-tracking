package com.sgenne.timetracking.project.model;


import com.sgenne.timetracking.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany
    private List<Activity> activities;

    @ManyToOne
    private User owner;

    public Project(String title, String description, User owner) {
        this.title = title;
        this.description = description;
        this.owner = owner;
    }
}
