package com.sgenne.timetracking.project.controller;

import com.sgenne.timetracking.project.model.Project;
import com.sgenne.timetracking.project.request.CreateProjectRequest;
import com.sgenne.timetracking.project.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.sgenne.timetracking.api.APIConstants.APPLICATION_JSON;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {
    public static final String PROJECT_ROOT_URL = "/api/v1/project";

    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
        Project project = projectService.getProjectById(projectId);

        return ResponseEntity.ok().body(project);
    }

    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<Void> createProject(@RequestBody CreateProjectRequest request) {
        Project project = projectService.createProject(request);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(PROJECT_ROOT_URL + "/"  + project.getId())
                        .toUriString());

        return ResponseEntity.created(uri).build();
    }



}
