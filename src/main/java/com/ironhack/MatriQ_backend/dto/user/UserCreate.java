package com.ironhack.MatriQ_backend.dto.user;

import com.ironhack.MatriQ_backend.enums.UserRole;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UserCreate(
        @NotBlank(message = "Full name cannot be blank")
        String fullName,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotNull(message = "Role cannot be null")
        UserRole role,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
        String phoneNumber
) {}