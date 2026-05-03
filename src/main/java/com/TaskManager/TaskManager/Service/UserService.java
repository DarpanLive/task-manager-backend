package com.TaskManager.TaskManager.Service;

import com.TaskManager.TaskManager.Dto.UserResponseDto;
import com.TaskManager.TaskManager.Dto.UserUpdateRequestDto;
import com.TaskManager.TaskManager.Entity.AppUser;

import java.util.List;

public interface UserService {
    AppUser getCurrentUser();
    AppUser getUserEntity(Long userId);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Long id, UserUpdateRequestDto request);
    void deleteUser(Long id);
}