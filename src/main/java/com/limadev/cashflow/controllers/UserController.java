package com.limadev.cashflow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limadev.cashflow.domain.services.UserService;
import com.limadev.cashflow.domain.user.User;
import com.limadev.cashflow.domain.user.UserDTO;
import com.limadev.cashflow.exception.BusinessException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Retorna dados do usuário", description = "Retorna os dados do usuário a partir do token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os dados do usuário"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
    })
    @GetMapping
    public ResponseEntity<UserDTO> getUser(HttpServletRequest request) throws BusinessException {
        User user = userService.getUser(request);
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail());

        return ResponseEntity.ok(userDTO);
    }
}
