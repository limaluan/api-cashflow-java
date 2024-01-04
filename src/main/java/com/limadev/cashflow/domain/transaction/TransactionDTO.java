package com.limadev.cashflow.domain.transaction;

import java.time.LocalDateTime;

import com.limadev.cashflow.domain.user.User;

public record TransactionDTO(
Double amount, String description,
String category, TransactionType type,
LocalDateTime createdAt,
User user)
{
}
