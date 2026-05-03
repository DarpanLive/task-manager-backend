package com.TaskManager.TaskManager.Controller.Impl;

import com.TaskManager.TaskManager.Controller.UserController;
import com.TaskManager.TaskManager.Dto.UserResponseDto;
import com.TaskManager.TaskManager.Dto.UserUpdateRequestDto;
import com.TaskManager.TaskManager.Entity.AppUser;
import com.TaskManager.TaskManager.Service.UserService;
import com.TaskManager.TaskManager.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // ✅ FIXED (NO MORE FILTER JUGAAD)
    @Override
    public UserResponseDto getCurrentUser() {
        AppUser user = userService.getCurrentUser();
        return userMapper.toResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // ✅ UPDATE USER
    @Override
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto request) {
        log.info("Updating user with id: {}", id);
        return userService.updateUser(id, request);
    }

    // ✅ DELETE USER
    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        userService.deleteUser(id);
    }
}