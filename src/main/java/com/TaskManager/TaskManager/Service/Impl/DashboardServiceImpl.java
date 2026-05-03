package com.TaskManager.TaskManager.Service.Impl;

import com.TaskManager.TaskManager.Dto.DashboardResponseDto;
import com.TaskManager.TaskManager.Enum.TaskStatus;
import com.TaskManager.TaskManager.Service.DashboardService;
import com.TaskManager.TaskManager.Service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final TaskService taskService;

    @Override
    public DashboardResponseDto getDashboard() {

        long total = taskService.countTasksForCurrentUser(null, false);
        long completed = taskService.countTasksForCurrentUser(TaskStatus.DONE, false);
        long todo = taskService.countTasksForCurrentUser(TaskStatus.TODO, false);
        long inProgress = taskService.countTasksForCurrentUser(TaskStatus.IN_PROGRESS, false);
        long overdue = taskService.countTasksForCurrentUser(null, true);

        Map<TaskStatus, Long> map = new EnumMap<>(TaskStatus.class);
        map.put(TaskStatus.TODO, todo);
        map.put(TaskStatus.IN_PROGRESS, inProgress);
        map.put(TaskStatus.DONE, completed);

        return DashboardResponseDto.builder()
                .totalTasks(total)
                .completed(completed)
                .pending(todo + inProgress)
                .overdue(overdue)
                .statusBreakdown(map)
                .overdueTasks(taskService.findOverdueTasksForCurrentUser())
                .build();
    }
}