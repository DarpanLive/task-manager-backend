package com.TaskManager.TaskManager.Dto;

import com.TaskManager.TaskManager.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final Role role;
}
