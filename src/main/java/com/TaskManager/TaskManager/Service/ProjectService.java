package com.TaskManager.TaskManager.Service;

import com.TaskManager.TaskManager.Dto.ProjectRequestDto;
import com.TaskManager.TaskManager.Dto.ProjectResponseDto;
import com.TaskManager.TaskManager.Entity.Project;
import com.TaskManager.TaskManager.Entity.AppUser;

import java.util.List;

public interface ProjectService {

    ProjectResponseDto createProject(ProjectRequestDto request);
    List<ProjectResponseDto> getProjects();
    ProjectResponseDto getProject(Long projectId);
    ProjectResponseDto updateProject(Long projectId, ProjectRequestDto request);
    ProjectResponseDto addMember(Long projectId, Long userId);
    void deleteProject(Long projectId);

    Project getProjectEntity(Long projectId);
    void ensureCanViewProject(Project project, AppUser user);
    void ensureCanManageProject(Project project, AppUser user);
    boolean isMember(Project project, AppUser user);
}