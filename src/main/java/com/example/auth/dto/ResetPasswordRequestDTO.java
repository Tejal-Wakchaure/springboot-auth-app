package com.example.auth.dto;

public record ResetPasswordRequestDTO(
        String token,
        String password
) {
}