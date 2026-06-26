package com.example.auth.dto;

public record UserResponseDTO(
        Integer id,
        String name,
        String email,
        boolean verified
) {
}