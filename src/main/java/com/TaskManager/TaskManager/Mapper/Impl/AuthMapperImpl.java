package com.TaskManager.TaskManager.Mapper.Impl;

import com.TaskManager.TaskManager.Dto.AuthResponseDto;
import com.TaskManager.TaskManager.Entity.AppUser;
import com.TaskManager.TaskManager.Mapper.AuthMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public AuthResponseDto toAuthResponse(AppUser user, String token) {
        return AuthResponseDto.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}