package com.TaskManager.TaskManager.Mapper.Impl;

import com.TaskManager.TaskManager.Dto.ProjectRequestDto;
import com.TaskManager.TaskManager.Dto.ProjectResponseDto;
import com.TaskManager.TaskManager.Entity.Project;
import com.TaskManager.TaskManager.Mapper.ProjectMapper;
import com.TaskManager.TaskManager.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectMapperImpl implements ProjectMapper {

    private final UserMapper userMapper;

    @Override
    public Project toEntity(ProjectRequestDto dto) {
        return Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public ProjectResponseDto toResponseDto(Project project) {
        return ProjectResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdBy(userMapper.toResponseDto(project.getCreatedBy()))
                .members(project.getMembers().stream()
                        .map(userMapper::toResponseDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void updateEntity(ProjectRequestDto dto, Project project) {
        if (dto.getDescription() != null) {
            project.setDescription(dto.getDescription());
        }
    }
}