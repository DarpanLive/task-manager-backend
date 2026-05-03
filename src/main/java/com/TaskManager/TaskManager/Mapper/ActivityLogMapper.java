package com.TaskManager.TaskManager.Mapper;

import com.TaskManager.TaskManager.Dto.ActivityLogResponseDto;
import com.TaskManager.TaskManager.Entity.ActivityLog;

public interface ActivityLogMapper {
    ActivityLogResponseDto toResponseDto(ActivityLog log);
}