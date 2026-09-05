package com.socialai.backend.service;

import com.socialai.backend.dto.AuthResponseDto;
import com.socialai.backend.dto.LoginRequest;
import com.socialai.backend.dto.RegisterRequest;
import com.socialai.backend.model.User;
import com.socialai.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponseDto register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail().toLowerCase())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String role = request.getRole() != null ? request.getRole().toLowerCase() : "citizen";

        User user = new User(
            request.getEmail().toLowerCase(),
            encodedPassword,
            request.getName(),
            role,
            request.getOrgId()
        );

        user = userRepository.save(user);

        String dummyToken = "jwt_token_" + UUID.randomUUID().toString();
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setEmail(user.getEmail());
        safeUser.setName(user.getName());
        safeUser.setRole(user.getRole());
        safeUser.setOrgId(user.getOrgId());

        return new AuthResponseDto(dummyToken, safeUser, "User registered successfully");
    }

    public AuthResponseDto login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail().toLowerCase())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String dummyToken = "jwt_token_" + UUID.randomUUID().toString();
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setEmail(user.getEmail());
        safeUser.setName(user.getName());
        safeUser.setRole(user.getRole());
        safeUser.setOrgId(user.getOrgId());

        return new AuthResponseDto(dummyToken, safeUser, "Login successful");
    }
}
