package com.example.auth.controller;

import com.example.auth.dto.*;
import com.example.auth.exception.InvalidCredentialsException;
import com.example.auth.exception.UserNotFoundException;
import com.example.auth.exception.UserNotVerifiedException;
import com.example.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO user = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Registration successful. Please verify your email.",
                        "user", user
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getProfile(Authentication authentication) {
        UserResponseDTO user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        userService.verifyEmail(token);
        return ResponseEntity.ok(Map.of("message", "Email verified successfully"));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody UserRequestDTO request) {
        userService.forgotPassword(request);
        return ResponseEntity.ok(Map.of("message", "Password reset email sent"));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        userService.resetPassword(request);
        return ResponseEntity.ok(Map.of("message", "Password reset successful"));
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<?> handleNotVerified(UserNotVerifiedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", ex.getMessage()));
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
    }
}