package com.limadev.cashflow.domain.transaction;

public record LastTransactionsDTO(
  Transaction lastCreditTransaction, 
  Transaction lastDebitTransaction) {
    
}
