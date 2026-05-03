package com.TaskManager.TaskManager.Service;

import com.TaskManager.TaskManager.Dto.TaskRequestDto;
import com.TaskManager.TaskManager.Dto.TaskResponseDto;
import com.TaskManager.TaskManager.Dto.TaskStatusUpdateRequestDto;
import com.TaskManager.TaskManager.Dto.TaskUpdateRequestDto;
import com.TaskManager.TaskManager.Enum.TaskPriority;
import com.TaskManager.TaskManager.Enum.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto request);

    Page<TaskResponseDto> getTasks(TaskStatus status,
                                   TaskPriority priority,
                                   Long assignedUserId,
                                   Pageable pageable);

    TaskResponseDto getTask(Long taskId);

    TaskResponseDto updateTask(Long taskId, TaskUpdateRequestDto request);

    TaskResponseDto updateTaskStatus(Long taskId, TaskStatusUpdateRequestDto request);

    void deleteTask(Long taskId);

    List<TaskResponseDto> findOverdueTasksForCurrentUser();

    long countTasksForCurrentUser(TaskStatus status, boolean overdueOnly);
}