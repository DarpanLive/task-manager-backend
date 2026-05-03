package com.TaskManager.TaskManager.Service.Impl;

import com.TaskManager.TaskManager.Config.JwtService;
import com.TaskManager.TaskManager.Dto.AuthResponseDto;
import com.TaskManager.TaskManager.Dto.LoginRequestDto;
import com.TaskManager.TaskManager.Dto.SignupRequestDto;
import com.TaskManager.TaskManager.Entity.AppUser;
import com.TaskManager.TaskManager.Enum.Role;
import com.TaskManager.TaskManager.Exceptions.BadRequestException;
import com.TaskManager.TaskManager.Mapper.AuthMapper;
import com.TaskManager.TaskManager.Mapper.UserMapper;
import com.TaskManager.TaskManager.Repository.UserRepository;
import com.TaskManager.TaskManager.Service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;

    @Override
    @Transactional
    public AuthResponseDto signup(SignupRequestDto request) {

        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Email is already registered");
        }

        AppUser user = new AppUser();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() == null ? Role.MEMBER : request.getRole());

        AppUser savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return authMapper.toAuthResponse(savedUser, token);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        String email = request.getEmail().trim().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.getPassword())
        );

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        String token = jwtService.generateToken(user);
        return authMapper.toAuthResponse(user, token);
    }
}