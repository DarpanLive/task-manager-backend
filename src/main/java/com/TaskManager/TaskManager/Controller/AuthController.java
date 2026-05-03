package com.TaskManager.TaskManager.Controller;

import com.TaskManager.TaskManager.Dto.AuthResponseDto;
import com.TaskManager.TaskManager.Dto.LoginRequestDto;
import com.TaskManager.TaskManager.Dto.SignupRequestDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/auth")
public interface AuthController {

    @PostMapping("/signup")
    AuthResponseDto signup(@Valid @RequestBody SignupRequestDto request);

    @PostMapping("/login")
    AuthResponseDto login(@Valid @RequestBody LoginRequestDto request);
}