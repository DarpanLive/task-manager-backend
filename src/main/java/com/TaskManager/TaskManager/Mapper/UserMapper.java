package com.TaskManager.TaskManager.Mapper;

import com.TaskManager.TaskManager.Dto.UserResponseDto;
import com.TaskManager.TaskManager.Entity.AppUser;

public interface UserMapper {
    AppUser toEntity(Object request); // optional
    UserResponseDto toResponseDto(AppUser user);
}