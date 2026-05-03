package com.TaskManager.TaskManager.Service.Impl;

import com.TaskManager.TaskManager.Dto.UserResponseDto;
import com.TaskManager.TaskManager.Dto.UserUpdateRequestDto;
import com.TaskManager.TaskManager.Entity.AppUser;
import com.TaskManager.TaskManager.Exceptions.ResourceNotFoundException;
import com.TaskManager.TaskManager.Mapper.UserMapper;
import com.TaskManager.TaskManager.Repository.UserRepository;
import com.TaskManager.TaskManager.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public AppUser getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }

    @Override
    public AppUser getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto request) {

        AppUser user = getUserEntity(id);

        if (request.getName() != null)
            user.setName(request.getName());

        if (request.getEmail() != null)
            user.setEmail(request.getEmail());

        if (request.getRole() != null)
            user.setRole(request.getRole());

        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        AppUser user = getUserEntity(id);
        userRepository.delete(user);
    }
}