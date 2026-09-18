package org.example.SpringApiTesting.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BookRequest(
        @NotBlank String isbn,
        @NotBlank String title,
        @NotBlank String author,
        String category,
        @Min(0) int totalQuantity
) {}
