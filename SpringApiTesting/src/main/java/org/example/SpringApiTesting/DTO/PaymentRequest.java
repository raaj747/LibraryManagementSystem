package org.example.SpringApiTesting.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull Long fineId,
        @NotBlank String paymentMethod
) {}
