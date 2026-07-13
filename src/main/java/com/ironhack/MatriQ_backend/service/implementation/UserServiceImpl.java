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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null) ? authentication.getName() : null;
    }

    private boolean isCurrentUserAdminOrManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MANAGER"));
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDTO);
    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        String currentUserEmail = getCurrentUserEmail();
        if (!isCurrentUserAdminOrManager() && (currentUserEmail == null || !currentUserEmail.equals(user.getEmail()))) {
            throw new AccessDeniedException("You do not have permission to view this profile.");
        }

        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponse createUser(UserCreate dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new ValidationException("Email already exists: " + dto.email());
        }
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(UUID id, UserUpdate dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        String currentUserEmail = getCurrentUserEmail();
        if (!isCurrentUserAdminOrManager() && (currentUserEmail == null || !currentUserEmail.equals(user.getEmail()))) {
            throw new AccessDeniedException("You do not have permission to update this profile.");
        }

        userMapper.updateEntity(dto, user);
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