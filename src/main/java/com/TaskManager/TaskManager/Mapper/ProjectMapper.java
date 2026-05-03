package com.TaskManager.TaskManager.Mapper;

import com.TaskManager.TaskManager.Dto.ProjectRequestDto;
import com.TaskManager.TaskManager.Dto.ProjectResponseDto;
import com.TaskManager.TaskManager.Entity.Project;

public interface ProjectMapper {
    Project toEntity(ProjectRequestDto dto);
    ProjectResponseDto toResponseDto(Project project);
    void updateEntity(ProjectRequestDto dto, Project project);
}