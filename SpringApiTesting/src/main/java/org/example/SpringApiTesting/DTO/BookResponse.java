package org.example.SpringApiTesting.DTO;

public record BookResponse(
        Long id,
        String isbn,
        String title,
        String author,
        String category,
        int totalQuantity,
        int availableQuantity,
        String status
) {}
