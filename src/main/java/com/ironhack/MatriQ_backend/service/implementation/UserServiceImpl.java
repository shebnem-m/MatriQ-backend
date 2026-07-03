package com.ironhack.MatriQ_backend.service.implementation;

import com.ironhack.MatriQ_backend.dto.user.UserCreate;
import com.ironhack.MatriQ_backend.dto.user.UserResponse;
import com.ironhack.MatriQ_backend.dto.user.UserUpdate;
import com.ironhack.MatriQ_backend.entity.User;
import com.ironhack.MatriQ_backend.exception.ResourceNotFoundException;
import com.ironhack.MatriQ_backend.exception.ValidationException;
import com.ironhack.MatriQ_backend.mapper.UserMapper;
import com.ironhack.MatriQ_backend.repository.UserRepository;
import com.ironhack.MatriQ_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDTO);
    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponse createUser(UserCreate dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new ValidationException("Email already exists: " + dto.email());
        }
        User user = userMapper.toEntity(dto);
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(UUID id, UserUpdate dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userMapper.updateEntity(dto,user);
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}