package com.limadev.cashflow.domain.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDTO(
    @Schema(example = "64ab7563-c893-4915-88e5-1caa629c5b70") String id, 
    @Schema(example = "User") String name, 
    @Schema(example = "user@email.com") String email) {
}
