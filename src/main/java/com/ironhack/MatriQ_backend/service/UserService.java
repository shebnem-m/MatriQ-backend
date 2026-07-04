package com.ironhack.MatriQ_backend.service;

import com.ironhack.MatriQ_backend.dto.user.UserCreate;
import com.ironhack.MatriQ_backend.dto.user.UserResponse;
import com.ironhack.MatriQ_backend.dto.user.UserUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface UserService {
    Page<UserResponse> getAllUsers(Pageable pageable);
    UserResponse getUserById(UUID id);
    UserResponse createUser(UserCreate dto);
    UserResponse updateUser(UUID id, UserUpdate dto);
    void deleteUser(UUID id);
}