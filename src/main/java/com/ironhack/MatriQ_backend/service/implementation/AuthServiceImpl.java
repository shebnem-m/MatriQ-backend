package com.ironhack.MatriQ_backend.service.implementation;

import com.ironhack.MatriQ_backend.dto.user.UserCreate;
import com.ironhack.MatriQ_backend.dto.user.UserResponse;
import com.ironhack.MatriQ_backend.entity.User;
import com.ironhack.MatriQ_backend.enums.UserRole;
import com.ironhack.MatriQ_backend.exception.InvalidCredentialsException;
import com.ironhack.MatriQ_backend.mapper.UserMapper;
import com.ironhack.MatriQ_backend.repository.UserRepository;
import com.ironhack.MatriQ_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse register(UserCreate request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new InvalidCredentialsException("A user with email " + request.email() + " already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setRole(UserRole.CUSTOMER);
        user.setBirthDate(request.birthDate());
        user.setPhoneNumber(request.phoneNumber());

        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        return userMapper.toResponseDTO(user);
    }

    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return userMapper.toResponseDTO(user);
    }
    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

}
