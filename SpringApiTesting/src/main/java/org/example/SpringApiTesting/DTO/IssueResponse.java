package org.example.SpringApiTesting.DTO;

import java.time.LocalDate;

public record IssueResponse(
        Long id,
        Long bookId,
        String bookTitle,
        Long memberId,
        String memberName,
        LocalDate issueDate,
        LocalDate dueDate,
        LocalDate returnDate,
        String status
) {}
