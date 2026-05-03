package com.TaskManager.TaskManager.Controller;

import com.TaskManager.TaskManager.Dto.UserResponseDto;
import com.TaskManager.TaskManager.Dto.UserUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping("/me")
    UserResponseDto getCurrentUser();

    @GetMapping("/get-all")
    List<UserResponseDto> getAllUsers();

    // ✅ NEW
    @PutMapping("/update/{id}")
    UserResponseDto updateUser(@PathVariable Long id,
                               @RequestBody UserUpdateRequestDto request);

    // ✅ NEW
    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable Long id);
}