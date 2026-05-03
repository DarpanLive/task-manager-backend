package com.TaskManager.TaskManager.Dto;

import com.TaskManager.TaskManager.Enum.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class DashboardResponseDto {
    private final long totalTasks;
    private final long completed;
    private final long pending;
    private final long overdue;
    private final Map<TaskStatus, Long> statusBreakdown;
    private final List<TaskResponseDto> overdueTasks;
}
