package com.example.auth.service;

import com.example.auth.dto.*;
import com.example.auth.entity.User;
import com.example.auth.exception.InvalidCredentialsException;
import com.example.auth.exception.UserAlreadyExistsException;
import com.example.auth.exception.UserNotFoundException;
import com.example.auth.exception.UserNotVerifiedException;
import com.example.auth.mapper.UserMapper;
import com.example.auth.repository.UserRepository;
import com.example.auth.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final EmailService emailService;

    @Value("${spring.frontend.url}")
    private String frontendURL;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtils jwtUtils, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
    }


    public UserResponseDTO registerUser(UserRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException(request.email());
        }

        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User savedUser = userRepository.save(user);

        String verificationToken = jwtUtils.generateToken(savedUser.getEmail());
        String verificationUrl = frontendURL + "/verifyEmail?token=" + verificationToken;

        emailService.sendVerificationEmail(savedUser.getEmail(), verificationUrl);
        return UserMapper.toResponse(savedUser);
    }

    public LoginResponseDTO loginUser(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new UserNotFoundException(request.email()));
        boolean matches = passwordEncoder.matches(
                request.password(),
                user.getPassword()
        );
        if (!matches) {
            throw new InvalidCredentialsException();
        }
        if (!user.isVerified()) {
            throw new UserNotVerifiedException();
        }
        String jwtToken = jwtUtils.generateToken(request.email());
        UserResponseDTO userResponse =
                new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.isVerified()
                );
        return new LoginResponseDTO(
                jwtToken,
                userResponse,
                "Login successful"
        );
    }

    public void verifyEmail(String token) {
        String email = jwtUtils.extractEmail(token);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        user.setVerified(true);
        userRepository.save(user);
    }

    public void forgotPassword(UserRequestDTO request) {
        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                request.email()
                        ));
        String token = jwtUtils.generatePasswordResetToken(user.getEmail());
        String resetUrl = frontendURL + "/resetPassword?token=" + token;
        emailService.sendPasswordResetEmail(
                user.getEmail(),
                resetUrl
        );
    }

    public void resetPassword(ResetPasswordRequestDTO request) {
        if (!jwtUtils.isValid(request.token())) {
            throw new RuntimeException("Invalid token");
        }
        String email = jwtUtils.extractEmail(request.token());
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        email
                                ));
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(email));
        return UserMapper.toResponse(user);
    }
}