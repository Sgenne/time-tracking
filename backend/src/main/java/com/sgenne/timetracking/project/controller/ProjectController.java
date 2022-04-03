package com.sgenne.timetracking.project.controller;

import com.sgenne.timetracking.project.model.Project;
import com.sgenne.timetracking.project.request.CreateProjectRequest;
import com.sgenne.timetracking.project.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ResponseEntity<Void> createProject(@RequestBody CreateProjectRequest request) {



        Project project = new Project();
        return ResponseEntity.ok().build();
    }



}
