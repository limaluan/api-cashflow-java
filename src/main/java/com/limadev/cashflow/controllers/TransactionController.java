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
import com.limadev.cashflow.transaction.Transaction;
import com.limadev.cashflow.transaction.TransactionDTO;
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

    @GetMapping
    public ResponseEntity<Map<String, List<Transaction>>> getUserTransactions(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        String sub = tokenService.validateToken(token);
        User user = (User) userRepository.findByEmail(sub);

        List<Transaction> transactions = transactionRepository.findAllByUserId(user.getId());

        Map<String, List<Transaction>> response = new HashMap<>();
        response.put("transactions", transactions);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO data,
            HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        String sub = tokenService.validateToken(token);
        User user = (User) userRepository.findByEmail(sub);

        Transaction transaction = new Transaction(data.amount(), data.description(), data.category(), data.type(),
                LocalDateTime.now(), user);

        transactionRepository.save(transaction);

        return ResponseEntity.ok(transaction);
    }
}
