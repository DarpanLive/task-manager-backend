package com.TaskManager.TaskManager.Controller.Impl;

import com.TaskManager.TaskManager.Controller.ActivityLogController;
import com.TaskManager.TaskManager.Dto.ActivityLogResponseDto;
import com.TaskManager.TaskManager.Service.ActivityLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ActivityLogControllerImpl implements ActivityLogController {

    private final ActivityLogService activityLogService;

    @Override
    public ResponseEntity<Page<ActivityLogResponseDto>> getLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        return ResponseEntity.ok(activityLogService.getLogs(pageable));
    }
}
