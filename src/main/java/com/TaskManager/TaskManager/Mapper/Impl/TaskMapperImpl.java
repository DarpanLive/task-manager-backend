package com.TaskManager.TaskManager.Mapper.Impl;

import com.TaskManager.TaskManager.Dto.TaskRequestDto;
import com.TaskManager.TaskManager.Dto.TaskResponseDto;
import com.TaskManager.TaskManager.Dto.TaskUpdateRequestDto;
import com.TaskManager.TaskManager.Entity.Task;
import com.TaskManager.TaskManager.Mapper.TaskMapper;
import com.TaskManager.TaskManager.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TaskMapperImpl implements TaskMapper {

    private final UserMapper userMapper;

    @Override
    public Task toEntity(TaskRequestDto dto) {
        return Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .deadline(dto.getDeadline())
                .status(dto.getStatus())
                .build();
    }

    @Override
    public TaskResponseDto toResponseDto(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .deadline(task.getDeadline())
                .overdue(task.getDeadline().isBefore(LocalDate.now()))
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .assignedTo(userMapper.toResponseDto(task.getAssignedTo()))
                .build();
    }

    @Override
    public void updateEntity(TaskUpdateRequestDto dto, Task task) {
        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getPriority() != null) {
            task.setPriority(dto.getPriority());
        }
        if (dto.getDeadline() != null) {
            task.setDeadline(dto.getDeadline());
        }
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
    }
}