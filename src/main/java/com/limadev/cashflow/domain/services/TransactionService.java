package com.limadev.cashflow.domain.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limadev.cashflow.domain.repositories.TransactionRepository;
import com.limadev.cashflow.domain.transaction.LastTransactionsDTO;
import com.limadev.cashflow.domain.transaction.Transaction;
import com.limadev.cashflow.domain.transaction.TransactionDTO;
import com.limadev.cashflow.domain.transaction.TransactionType;
import com.limadev.cashflow.domain.user.BalanceDTO;
import com.limadev.cashflow.domain.user.User;
import com.limadev.cashflow.exception.BusinessException;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public Map<String, List<TransactionDTO>> getUserTransactions(String userId) {
        List<TransactionDTO> transactions = transactionRepository.findAllByUserId(userId).stream().map(transaction -> {
            return new TransactionDTO(transaction.getAmount(), transaction.getDescription(), transaction.getCategory(),
                    transaction.getType(), transaction.getCreatedAt());
        }).collect(Collectors.toList());

        Map<String, List<TransactionDTO>> response = new HashMap<>();
        response.put("transactions", transactions);

        return response;
    }

    public LastTransactionsDTO getLastTransactions(String userId) {
        Transaction lastCreditTransaction = this
                .findLastTransactionByType(transactionRepository.findAllByUserId(userId), TransactionType.credit);
        Transaction lastDebitTransaction = this
                .findLastTransactionByType(transactionRepository.findAllByUserId(userId), TransactionType.debit);

        return new LastTransactionsDTO(lastCreditTransaction, lastDebitTransaction);
    }

    public BalanceDTO getUserBalance(String userId) {
        List<Transaction> userTransactions = transactionRepository.findAllByUserId(userId);

        final double[] credits = { 0.0 };
        final double[] debits = { 0.0 };

        userTransactions.stream().forEach(transaction -> {
            if (transaction.getType() == TransactionType.credit) {
                credits[0] += transaction.getAmount();
            } else {
                debits[0] += transaction.getAmount();
            }
        });

        return new BalanceDTO(credits[0], debits[0], credits[0] - debits[0]);
    }

    public Transaction createTransaction(Transaction data, User user) throws BusinessException {
        BalanceDTO balance = this.getUserBalance(user.getId());

        if (data.getType() == TransactionType.debit && data.getAmount() > balance.balance())
            throw new BusinessException("Saldo insuficiente");
        else if (data.getDescription().length() < 2) {
            throw new BusinessException("A descrição deve possuir mais de dois caracteres");
        } else if (data.getAmount() < 0) {
            throw new BusinessException("Valor de transação deve ser maior que zero");
        } else if (data.getCategory().length() < 2) {
            throw new BusinessException("A categoria deve possuir mais de dois caracteres");
        }

        Transaction transaction = new Transaction(data.getAmount(), data.getDescription(), data.getCategory(),
                data.getType(),
                LocalDateTime.now(), user);

        transactionRepository.save(transaction);

        return transaction;
    }

    private Transaction findLastTransactionByType(List<Transaction> transactions, TransactionType type) {
        return transactions.stream()
                .filter(transaction -> type.equals(transaction.getType()))
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(new Transaction());
    }
}
