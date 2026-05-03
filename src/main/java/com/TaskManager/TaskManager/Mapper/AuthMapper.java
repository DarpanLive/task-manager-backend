package com.TaskManager.TaskManager.Mapper;

import com.TaskManager.TaskManager.Dto.AuthResponseDto;
import com.TaskManager.TaskManager.Entity.AppUser;

public interface AuthMapper {
    AuthResponseDto toAuthResponse(AppUser user, String token);
}