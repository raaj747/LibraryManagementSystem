package org.example.SpringApiTesting.DTO;

public record LoginResponse(
        String token,
        String tokenType,
        Long userId,
        String name,
        String email,
        String role
) {}
