package com.TaskManager.TaskManager.Mapper;

import com.TaskManager.TaskManager.Dto.TaskRequestDto;
import com.TaskManager.TaskManager.Dto.TaskResponseDto;
import com.TaskManager.TaskManager.Dto.TaskUpdateRequestDto;
import com.TaskManager.TaskManager.Entity.Task;

public interface TaskMapper {
    Task toEntity(TaskRequestDto dto);
    TaskResponseDto toResponseDto(Task task);
    void updateEntity(TaskUpdateRequestDto dto, Task task);
}