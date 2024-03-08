package com.limadev.cashflow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.limadev.cashflow.domain.services.TransactionService;
import com.limadev.cashflow.domain.services.UserService;
import com.limadev.cashflow.domain.transaction.LastTransactionsDTO;
import com.limadev.cashflow.domain.transaction.Transaction;
import com.limadev.cashflow.domain.transaction.TransactionDTO;
import com.limadev.cashflow.domain.user.BalanceDTO;
import com.limadev.cashflow.domain.user.User;
import com.limadev.cashflow.exception.BusinessException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/user/transactions")
@RestController
@CrossOrigin
@SecurityRequirement(name = "bearer-key")
public class TransactionController {
        @Autowired
        UserService userService;

        @Autowired
        TransactionService transactionService;

        @Operation(summary = "Retorna transações do usuário", description = "Retorna todas transações do usuário")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna todas transações do usuário")
        })
        @GetMapping
        public ResponseEntity<Page<TransactionDTO>> getUserTransactions(
                        HttpServletRequest request,

                        @RequestHeader("Authorization") String authorizationHeader,

                        @Parameter(description = "<b>Descrição da transação a ser buscada</b>", example = "Projeto Web") 
                        @RequestParam(required = false) String transactionDescription,

                        Pageable pageable)
                        throws BusinessException {
                User user = userService.getUser(request);

                if (transactionDescription != null && !transactionDescription.isEmpty()) {
                        return ResponseEntity.ok(transactionService.getUserTransactions(user.getId(),
                                        transactionDescription, pageable));
                } else {
                        return ResponseEntity.ok(transactionService.getUserTransactions(user.getId(), pageable));
                }
        }

        @Operation(summary = "Retorna o saldo do usuário", description = "Retorna o saldo do usuário")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o saldo do usuário")
        })
        @GetMapping("/balance")
        public ResponseEntity<BalanceDTO> getUserBalance(
                        HttpServletRequest request,
                        @RequestHeader("Authorization") String authorizationHeader) throws BusinessException {
                User user = userService.getUser(request);

                return ResponseEntity.ok(transactionService.getUserBalance(user.getId()));
        }

        @Operation(summary = "Retorna as últimas transações do usuário", description = "<ul>Retorna as últimas transações feitas pelo usuário " +
        "sendo elas:" +
        "<li><b>lastCreditTransaction</b>: Todos os dados da última <b>Entrada</b></li>" +
        "<li><b>lastDebitTransaction</b>: Todos os dados da última <b>Saída</b></li></ul>")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna as últimas transações feitas pelo usuário")
        })
        @GetMapping("/lastTransactions")
        public ResponseEntity<LastTransactionsDTO> getLastTransactions(
                        HttpServletRequest request,
                        @RequestHeader("Authorization") String authorizationHeader)
                        throws BusinessException {
                User user = userService.getUser(request);

                return ResponseEntity.ok(transactionService.getLastTransactions(user.getId()));
        }

        @Operation(summary = "Cria uma transação", description = "Cria uma transação")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Cria um produto no banco de dados"),
        })
        @PostMapping
        public ResponseEntity<Transaction> createTransaction(
                        @RequestHeader("Authorization") String authorizationHeader,

                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
                                        "<ul>" +
                                        "<li>**__amount__**: Valor da Transação.</li>" +
                                        "<ul>" +
                                        "<li>**O valor deve ser maior que 0.**</li>" +
                                        "<li>**O campo não pode ser vazio**</li>" +
                                        "</ul>" +
                                        "</li>" +
                                        "<li>**__description__**: Descrição da Transação.</li>" +
                                        "<ul>" +
                                        "<li>**Deve possuir mais de 2 caracteres.**</li>" +
                                        "<li>**O campo não pode ser vazio**</li>" +
                                        "</ul>" +
                                        "</li>" +
                                        "<li>**__type__**: Tipo da Transação.</li>" +
                                        "<ul>" +
                                        "<li>**O tipo só pode ser credit ou debit.**</li>" +
                                        "</ul>" +
                                        "<li>**__category__**: Categoria da Transação.</li>" +
                                        "<ul>" +
                                        "<li>**Deve possuir mais de 2 caracteres.**</li>" +
                                        "<li>**O campo não pode ser vazio**</li>" +
                                        "</li>" +
                                        "</ul>" +
                                        "</ul>") 
                        @RequestBody Transaction data,

                        HttpServletRequest request) throws BusinessException {
                User user = userService.getUser(request);

                return ResponseEntity.status(201).body(transactionService.createTransaction(data, user));
        }
}
