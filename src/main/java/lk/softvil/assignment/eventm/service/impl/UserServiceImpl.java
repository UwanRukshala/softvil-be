package lk.softvil.assignment.eventm.service.impl;

import lk.softvil.assignment.eventm.dto.UserRegistrationRequest;
import lk.softvil.assignment.eventm.dto.UserResponse;
import lk.softvil.assignment.eventm.exception.EmailAlreadyExistsException;
import lk.softvil.assignment.eventm.exception.UserNotFoundException;
import lk.softvil.assignment.eventm.mapper.UserMapper;
import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.model.enums.UserRole;
import lk.softvil.assignment.eventm.repository.UserRepository;
import lk.softvil.assignment.eventm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse registerUserByAdmin(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }
        UserRole role=(request.bothAdminAndHost()?UserRole.ADMIN:request.role());

        User user = User.builder()
                .name(request.username())
                .email(request.email())
                .role(role)
                .bothAdminAndHost(request.bothAdminAndHost())
                .createdAt(LocalDateTime.now())
                .status(true)
                .build();


        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = User.builder()
                .name(request.username())
                .email(request.email())
                .role(UserRole.USER)
                .createdAt(LocalDateTime.now())
                .status(true)
                .build();


        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);

    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    public Page<UserResponse> getUsersByRole(UserRole role, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );
        return userRepository.findByRole(role, pageable)
                .map(userMapper::toResponse);
    }
}