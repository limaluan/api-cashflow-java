package com.limadev.cashflow.domain.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterDTO(
        @Schema(example = "user@email.com") String email,
        @Schema(example = "User") String name,
        @Schema(example = "User@123") String password) {}
