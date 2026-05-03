package com.TaskManager.TaskManager.Dto;

import com.TaskManager.TaskManager.Enum.TaskPriority;
import com.TaskManager.TaskManager.Enum.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@Getter
@Setter
public class TaskUpdateRequestDto {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;

    @FutureOrPresent
    private LocalDate deadline;

    private Long assignedToId;
}
