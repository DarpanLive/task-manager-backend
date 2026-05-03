package com.TaskManager.TaskManager.Dto;

import com.TaskManager.TaskManager.Enum.TaskPriority;
import com.TaskManager.TaskManager.Enum.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class TaskResponseDto {
    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final LocalDate deadline;
    private final boolean overdue;
    private final Long projectId;
    private final String projectName;
    private final UserResponseDto assignedTo;
}