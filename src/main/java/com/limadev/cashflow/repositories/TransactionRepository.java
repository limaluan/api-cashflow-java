package com.limadev.cashflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limadev.cashflow.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
