package com.TaskManager.TaskManager.Controller.Impl;

import com.TaskManager.TaskManager.Controller.TaskController;
import com.TaskManager.TaskManager.Dto.*;
import com.TaskManager.TaskManager.Enum.TaskPriority;
import com.TaskManager.TaskManager.Enum.TaskStatus;
import com.TaskManager.TaskManager.Service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    @Override
    public TaskResponseDto create(TaskRequestDto request) {
        return taskService.createTask(request);
    }

    @Override
    public Page<TaskResponseDto> getTasks(TaskStatus status,
                                          TaskPriority priority,
                                          Long assignedUserId,
                                          Pageable pageable) {
        return taskService.getTasks(status, priority, assignedUserId, pageable);
    }

    @Override
    public TaskResponseDto get(Long id) {
        return taskService.getTask(id);
    }

    @Override
    public TaskResponseDto update(Long id, TaskUpdateRequestDto request) {
        return taskService.updateTask(id, request);
    }

    @Override
    public TaskResponseDto updateStatus(Long id, TaskStatusUpdateRequestDto request) {
        return taskService.updateTaskStatus(id, request);
    }

    @Override
    public void delete(Long id) {
        taskService.deleteTask(id);
    }
}