package com.TaskManager.TaskManager.Controller;

import com.TaskManager.TaskManager.Dto.ActivityLogResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/activity-logs")
public interface ActivityLogController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<ActivityLogResponseDto>> getLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );
}
