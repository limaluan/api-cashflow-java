package com.limadev.cashflow.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limadev.cashflow.domain.transaction.Transaction;
import com.limadev.cashflow.domain.transaction.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByUserId(String userId);
    List<Transaction> findAllByType(TransactionType type);
}