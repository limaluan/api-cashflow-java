package com.limadev.cashflow.domain.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record BalanceDTO(
  @Schema(example = "2007") Double credits, 
  @Schema(example = "169") Double debits, 
  @Schema(example = "1838") Double balance) {}
