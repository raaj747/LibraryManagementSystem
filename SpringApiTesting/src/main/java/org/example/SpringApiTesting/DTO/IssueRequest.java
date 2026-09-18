package org.example.SpringApiTesting.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record IssueRequest(
        @NotNull Long bookId,
        Long memberId,
        @NotNull @FutureOrPresent LocalDate dueDate
) {}
