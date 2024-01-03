package com.limadev.cashflow.transaction;

public record LastTransactionsDTO(Transaction lastCreditTransaction, Transaction lastDebitTransaction) {
    
}
