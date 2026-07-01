package com.ironhack.MatriQ_backend.dto.user;

import com.ironhack.MatriQ_backend.entity.UserRole;
import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String fullName,
        String email,
        UserRole role,
        LocalDate birthDate,
        String phoneNumber
) {}
