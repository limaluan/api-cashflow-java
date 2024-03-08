package com.limadev.cashflow.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.limadev.cashflow.domain.transaction.Transaction;
import com.limadev.cashflow.domain.transaction.TransactionDTO;
import com.limadev.cashflow.domain.transaction.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByUserId(String userId);

    Page<TransactionDTO> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    Page<TransactionDTO> findByUserIdAndDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(
            String userId,
            String transactionDescription,
            Pageable pageable);

    List<Transaction> findAllByType(TransactionType type);
}
