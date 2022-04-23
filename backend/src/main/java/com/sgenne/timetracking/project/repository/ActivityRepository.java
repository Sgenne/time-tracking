package com.sgenne.timetracking.project.repository;

import com.sgenne.timetracking.project.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM Activity a WHERE a.project.id = ?1")
    List<Activity> getActivitiesByProjectId(Long projectId);
}
