package com.TaskManager.TaskManager.Dto;

import com.TaskManager.TaskManager.Enum.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TaskStatusUpdateRequestDto {

    @NotNull
    private TaskStatus status;
}
