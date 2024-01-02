package com.limadev.cashflow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limadev.cashflow.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByUserId(String userId);
}
