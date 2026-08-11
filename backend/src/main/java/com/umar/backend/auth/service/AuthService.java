package com.umar.backend.auth.service;

import com.umar.backend.auth.dto.AuthResponseDTO;
import com.umar.backend.auth.dto.LoginRequestDTO;
import com.umar.backend.auth.dto.SignupRequestDTO;
import com.umar.backend.security.jwt.JwtUtil;
import com.umar.backend.user.entity.User;
import com.umar.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO signup(SignupRequestDTO request) {

        log.info("Registering new user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        log.info("User registered successfully with email: {}",
                request.getEmail());
        return new AuthResponseDTO(
                null,
                "User registered successfully");
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        log.info("Login attempt for email: {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        log.info("User authenticated successfully: {}",
                request.getEmail());
        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponseDTO(
                token,
                "Login successful"
        );
    }
}