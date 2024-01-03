package com.limadev.cashflow.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limadev.cashflow.repositories.TransactionRepository;
import com.limadev.cashflow.repositories.UserRepository;
import com.limadev.cashflow.services.TokenService;
import com.limadev.cashflow.services.TransactionService;
import com.limadev.cashflow.services.UserService;
import com.limadev.cashflow.transaction.LastTransactionsDTO;
import com.limadev.cashflow.transaction.Transaction;
import com.limadev.cashflow.transaction.TransactionDTO;
import com.limadev.cashflow.transaction.TransactionType;
import com.limadev.cashflow.user.BalanceDTO;
import com.limadev.cashflow.user.User;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/user/transactions")
@RestController
@CrossOrigin
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Map<String, List<Transaction>>> getUserTransactions(HttpServletRequest request) {
        User user = userService.getUser(request);

        List<Transaction> transactions = transactionRepository.findAllByUserId(user.getId());

        Map<String, List<Transaction>> response = new HashMap<>();
        response.put("transactions", transactions);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceDTO> getUserBalance(HttpServletRequest request) {
        User user = userService.getUser(request);

        List<Transaction> userTransactions = transactionRepository.findAllByUserId(user.getId());

        final double[] credits = { 0.0 };
        final double[] debits = { 0.0 };

        userTransactions.forEach(transaction -> {
            if (transaction.getType() == TransactionType.credit) {
                credits[0] += transaction.getAmount();
            } else {
                debits[0] += transaction.getAmount();
            }
        });

        return ResponseEntity.ok(new BalanceDTO(credits[0], debits[0], credits[0] - debits[0]));
    }

    @GetMapping("/lastTransactions")
    public ResponseEntity<LastTransactionsDTO> getLastTransactions(HttpServletRequest request) {
        User user = userService.getUser(request);

        Transaction lastCreditTransaction = transactionService
                .findLastTransactionByType(transactionRepository.findAllByUserId(user.getId()), TransactionType.credit);
        Transaction lastDebitTransaction = transactionService
                .findLastTransactionByType(transactionRepository.findAllByUserId(user.getId()), TransactionType.debit);

        return ResponseEntity.ok(new LastTransactionsDTO(lastCreditTransaction, lastDebitTransaction));
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO data,
            HttpServletRequest request) {
        User user = userService.getUser(request);

        Transaction transaction = new Transaction(data.amount(), data.description(), data.category(), data.type(),
                LocalDateTime.now(), user);

        transactionRepository.save(transaction);

        return ResponseEntity.ok(transaction);
    }
}
