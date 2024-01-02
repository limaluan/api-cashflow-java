package com.limadev.cashflow.transaction;

import java.time.LocalDateTime;

import com.limadev.cashflow.user.User;

public record TransactionDTO(
Double amount, String description,
String category, TransactionType type,
LocalDateTime createdAt,
User user)
{
}
