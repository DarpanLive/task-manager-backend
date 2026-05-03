package com.TaskManager.TaskManager.Service.Impl;

import com.TaskManager.TaskManager.Dto.ActivityLogResponseDto;
import com.TaskManager.TaskManager.Entity.ActivityLog;
import com.TaskManager.TaskManager.Entity.AppUser;
import com.TaskManager.TaskManager.Mapper.ActivityLogMapper;
import com.TaskManager.TaskManager.Repository.ActivityLogRepository;
import com.TaskManager.TaskManager.Service.ActivityLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final ActivityLogMapper activityLogMapper;

    @Override
    @Transactional
    public void log(String action, AppUser user) {

        ActivityLog log = ActivityLog.builder()
                .action(action)
                .timestamp(LocalDateTime.now())
                .user(user)
                .build();

        activityLogRepository.save(log);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityLogResponseDto> getLogs(Pageable pageable) {

        return activityLogRepository.findAll(pageable)
                .map(activityLogMapper::toResponseDto);
    }
}