package com.TaskManager.TaskManager.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ActivityLogResponseDto {
    private final Long id;
    private final String action;
    private final LocalDateTime timestamp;
    private final UserResponseDto user;
}
