package com.TaskManager.TaskManager.Controller.Impl;

import com.TaskManager.TaskManager.Controller.DashboardController;
import com.TaskManager.TaskManager.Dto.DashboardResponseDto;
import com.TaskManager.TaskManager.Service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DashboardControllerImpl implements DashboardController {

    private final DashboardService dashboardService;

    @Override
    public DashboardResponseDto getDashboard() {
        return dashboardService.getDashboard();
    }
}