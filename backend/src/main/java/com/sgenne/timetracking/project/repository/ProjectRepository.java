package com.sgenne.timetracking.project.repository;

import com.sgenne.timetracking.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {



}
