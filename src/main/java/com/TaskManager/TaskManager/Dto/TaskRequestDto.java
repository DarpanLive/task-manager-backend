package com.TaskManager.TaskManager.Dto;

import com.TaskManager.TaskManager.Enum.TaskPriority;
import com.TaskManager.TaskManager.Enum.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class TaskRequestDto {

    @NotBlank
    private String title;

    private String description;

    private TaskStatus status = TaskStatus.TODO;

    @NotNull
    private TaskPriority priority;

    @NotNull
    @FutureOrPresent
    private LocalDate deadline;

    @NotNull
    private Long assignedToId;

    @NotNull
    private Long projectId;
}
