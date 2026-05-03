package com.TaskManager.TaskManager.Controller;

import com.TaskManager.TaskManager.Dto.DashboardResponseDto;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/dashboard")
public interface DashboardController {

    @GetMapping
    DashboardResponseDto getDashboard();
}