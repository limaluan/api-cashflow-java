package com.limadev.cashflow.domain.transaction;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public record TransactionDTO(
        @Schema(example = "12.50") Double amount, 
        @Schema(example = "Compras do mês") String description,
        @Schema(example = "Compras") String category, 
        @Schema(example = "debit") TransactionType type,
        @Schema(example = "2024-03-06 20:26:23.630244") LocalDateTime createdAt) {
}
