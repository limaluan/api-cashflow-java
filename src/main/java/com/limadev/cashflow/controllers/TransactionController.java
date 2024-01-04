package com.limadev.cashflow.controllers;

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

import com.limadev.cashflow.domain.services.TransactionService;
import com.limadev.cashflow.domain.services.UserService;
import com.limadev.cashflow.domain.transaction.LastTransactionsDTO;
import com.limadev.cashflow.domain.transaction.Transaction;
import com.limadev.cashflow.domain.transaction.TransactionDTO;
import com.limadev.cashflow.domain.user.BalanceDTO;
import com.limadev.cashflow.domain.user.User;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/user/transactions")
@RestController
@CrossOrigin
public class TransactionController {
    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Map<String, List<Transaction>>> getUserTransactions(HttpServletRequest request) {
        User user = userService.getUser(request);

        return ResponseEntity.ok(transactionService.getUserTransactions(user.getId()));
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceDTO> getUserBalance(HttpServletRequest request) {
        User user = userService.getUser(request);

        return ResponseEntity.ok(transactionService.getUserBalance(user.getId()));
    }

    @GetMapping("/lastTransactions")
    public ResponseEntity<LastTransactionsDTO> getLastTransactions(HttpServletRequest request) {
        User user = userService.getUser(request);

        return ResponseEntity.ok(transactionService.getLastTransactions(user.getId()));
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO data,
            HttpServletRequest request) {
        User user = userService.getUser(request);

        return ResponseEntity.ok(transactionService.createTransaction(data, user));
    }
}
