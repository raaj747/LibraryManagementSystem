package org.example.SpringApiTesting.DTO;

public record UserResponse(
        Long id,
        String name,
        String email,
        String role,
        boolean enabled
) {}
