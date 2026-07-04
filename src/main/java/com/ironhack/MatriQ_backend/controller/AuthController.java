package com.ironhack.MatriQ_backend.controller;

import com.ironhack.MatriQ_backend.dto.auth.AuthResponse;
import com.ironhack.MatriQ_backend.dto.auth.LoginRequest;
import com.ironhack.MatriQ_backend.dto.user.UserCreate;
import com.ironhack.MatriQ_backend.dto.user.UserResponse;
import com.ironhack.MatriQ_backend.enums.UserRole;
import com.ironhack.MatriQ_backend.security.JwtTokenProvider;
import com.ironhack.MatriQ_backend.service.AuthService;
import com.ironhack.MatriQ_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserCreate request) {
        UserRole role = UserRole.CUSTOMER;
        if (request.role() != null) {
            role = UserRole.valueOf(request.role().toString().toUpperCase());
        }

        UserResponse user = authService.register(request);

        String token = jwtTokenProvider.generateToken(
                user.email(),
                user.role().name()
        );

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(user.email());
        response.setRole(user.role().name());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );

        UserResponse user = authService.findByEmail(request.getEmail());

        String token = jwtTokenProvider.generateToken(
                user.email(),
                user.role().name()
        );

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(user.email());
        response.setRole(user.role().name());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return authService.findByEmail(email);
    }
}
