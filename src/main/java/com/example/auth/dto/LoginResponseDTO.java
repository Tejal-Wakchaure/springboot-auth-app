package com.example.auth.dto;

public record LoginResponseDTO(
        String token,
        UserResponseDTO user,
        String message
) {
}