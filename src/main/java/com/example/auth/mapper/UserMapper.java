package com.example.auth.mapper;

import com.example.auth.dto.UserRequestDTO;
import com.example.auth.dto.UserResponseDTO;
import com.example.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public final class UserMapper {

    private UserMapper() {}

    public static User toEntity(UserRequestDTO request) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .verified(false)
                .build();
    }

    public static UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isVerified()
        );
    }
}