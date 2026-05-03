package com.TaskManager.TaskManager.Controller;

import com.TaskManager.TaskManager.Dto.*;
import com.TaskManager.TaskManager.Enum.TaskPriority;
import com.TaskManager.TaskManager.Enum.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/tasks")
public interface TaskController {

    @PostMapping("/add")
    TaskResponseDto create(@Valid @RequestBody TaskRequestDto request);

    @GetMapping("/get-all")
    Page<TaskResponseDto> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long assignedUserId,
            Pageable pageable
    );

    @GetMapping("/get-by/{id}")
    TaskResponseDto get(@PathVariable Long id);

    @PutMapping("/update/{id}")
    TaskResponseDto update(@PathVariable Long id,
                           @RequestBody TaskUpdateRequestDto request);

    @PatchMapping("/update/{id}/status")
    TaskResponseDto updateStatus(@PathVariable Long id,
                                 @RequestBody TaskStatusUpdateRequestDto request);

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id);
}