package com.TaskManager.TaskManager.Repository;

import com.TaskManager.TaskManager.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByMembers_Id(Long userId);
}
