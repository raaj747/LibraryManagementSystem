package org.example.SpringApiTesting.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FineResponse(
        Long id,
        Long issueId,
        Long memberId,
        String memberName,
        BigDecimal amount,
        String status,
        LocalDateTime createdAt,
        LocalDateTime paidAt
) {}
