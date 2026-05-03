package com.TaskManager.TaskManager.Controller.Impl;

import com.TaskManager.TaskManager.Controller.ProjectController;
import com.TaskManager.TaskManager.Dto.ProjectRequestDto;
import com.TaskManager.TaskManager.Dto.ProjectResponseDto;
import com.TaskManager.TaskManager.Service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    @Override
    public ProjectResponseDto create(ProjectRequestDto request) {
        return projectService.createProject(request);
    }

    @Override
    public List<ProjectResponseDto> getAll() {
        return projectService.getProjects();
    }

    @Override
    public ProjectResponseDto get(Long id) {
        return projectService.getProject(id);
    }

    @Override
    public ProjectResponseDto update(Long id, ProjectRequestDto request) {
        return projectService.updateProject(id, request);
    }

    @Override
    public ProjectResponseDto addMember(Long id, Long userId) {
        return projectService.addMember(id, userId);
    }

    @Override
    public void delete(Long id) {
        projectService.deleteProject(id);
    }
}