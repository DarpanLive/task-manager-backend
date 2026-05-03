package com.TaskManager.TaskManager.Controller.Impl;

import com.TaskManager.TaskManager.Controller.AuthController;
import com.TaskManager.TaskManager.Dto.AuthResponseDto;
import com.TaskManager.TaskManager.Dto.LoginRequestDto;
import com.TaskManager.TaskManager.Dto.SignupRequestDto;
import com.TaskManager.TaskManager.Service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public AuthResponseDto signup(SignupRequestDto request) {
        return authService.signup(request);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        return authService.login(request);
    }
}