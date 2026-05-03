package com.TaskManager.TaskManager.Service;

import com.TaskManager.TaskManager.Dto.AuthResponseDto;
import com.TaskManager.TaskManager.Dto.LoginRequestDto;
import com.TaskManager.TaskManager.Dto.SignupRequestDto;

public interface AuthService {
    AuthResponseDto signup(SignupRequestDto request);
    AuthResponseDto login(LoginRequestDto request);
}
