package com.limadev.cashflow.services;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.limadev.cashflow.transaction.Transaction;
import com.limadev.cashflow.transaction.TransactionType;

@Service
public class TransactionService {
    public Transaction findLastTransactionByType(List<Transaction> transactions, TransactionType type) {
        return transactions.stream()
                .filter(transaction -> type.equals(transaction.getType()))
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(new Transaction());
    }
}
