package lk.softvil.assignment.eventm.controller;


import lk.softvil.assignment.eventm.dto.UserRegistrationRequest;
import lk.softvil.assignment.eventm.dto.UserResponse;
import lk.softvil.assignment.eventm.model.enums.UserRole;
import lk.softvil.assignment.eventm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/registerByAdmin")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUserByAdmin(@RequestBody UserRegistrationRequest request) {
        return userService.registerUserByAdmin(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable UUID id) {
        return userService.getUserById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<UserResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userService.getAllUsers(page, size);
    }

    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    @GetMapping("/by-role/{role}")
    public Page<UserResponse> getUsersByRole(
            @PathVariable UserRole role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userService.getUsersByRole(role, page, size);
    }
}