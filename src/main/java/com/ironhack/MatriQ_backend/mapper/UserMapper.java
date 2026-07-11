package com.ironhack.MatriQ_backend.mapper;

import com.ironhack.MatriQ_backend.dto.user.UserCreate;
import com.ironhack.MatriQ_backend.dto.user.UserResponse;
import com.ironhack.MatriQ_backend.dto.user.UserUpdate;
import com.ironhack.MatriQ_backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserCreate dto) {
        return User.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .password(dto.password())
                .role(dto.role())
                .birthDate(dto.birthDate())
                .phoneNumber(dto.phoneNumber())
                .build();
    }

    public UserResponse toResponseDTO(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getBirthDate(),
                user.getPhoneNumber()
        );
    }

    public void updateEntity(UserUpdate dto, User user) {
        user.setFullName(dto.fullName());
        user.setBirthDate(dto.birthDate());
        user.setPhoneNumber(dto.phoneNumber());
    }
}