package com.TaskManager.TaskManager.Mapper.Impl;

import com.TaskManager.TaskManager.Dto.ActivityLogResponseDto;
import com.TaskManager.TaskManager.Entity.ActivityLog;
import com.TaskManager.TaskManager.Mapper.ActivityLogMapper;
import com.TaskManager.TaskManager.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityLogMapperImpl implements ActivityLogMapper {

    private final UserMapper userMapper;

    @Override
    public ActivityLogResponseDto toResponseDto(ActivityLog log) {
        return ActivityLogResponseDto.builder()
                .id(log.getId())
                .action(log.getAction())
                .timestamp(log.getTimestamp())
                .user(userMapper.toResponseDto(log.getUser()))
                .build();
    }
}