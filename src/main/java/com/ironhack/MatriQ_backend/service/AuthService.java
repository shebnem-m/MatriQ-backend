package com.ironhack.MatriQ_backend.service;

import com.ironhack.MatriQ_backend.dto.user.UserCreate;
import com.ironhack.MatriQ_backend.dto.user.UserResponse;
import com.ironhack.MatriQ_backend.entity.User;

public interface AuthService {
    UserResponse register(UserCreate request);
    UserResponse findByEmail(String email);
    boolean checkPassword(User user, String rawPassword);
}
