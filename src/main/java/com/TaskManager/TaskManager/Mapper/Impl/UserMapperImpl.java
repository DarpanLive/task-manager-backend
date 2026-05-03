package com.TaskManager.TaskManager.Mapper.Impl;

import com.TaskManager.TaskManager.Dto.UserResponseDto;
import com.TaskManager.TaskManager.Entity.AppUser;
import com.TaskManager.TaskManager.Mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public AppUser toEntity(Object request) {
        return null;
    }

    @Override
    public UserResponseDto toResponseDto(AppUser user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}