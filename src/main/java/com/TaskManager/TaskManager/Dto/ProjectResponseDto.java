package com.TaskManager.TaskManager.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class ProjectResponseDto {
    private final Long id;
    private final String name;
    private final String description;
    private final UserResponseDto createdBy;
    private final Set<UserResponseDto> members;
}
