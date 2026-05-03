package com.TaskManager.TaskManager.Dto;

import com.TaskManager.TaskManager.Enum.Role;
import lombok.Data;
import lombok.Getter;

@Data
public class UserUpdateRequestDto {
    private String name;
    private String email;
    private Role role;
}