package com.limadev.cashflow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limadev.cashflow.domain.services.UserService;
import com.limadev.cashflow.domain.user.AuthenticationDTO;
import com.limadev.cashflow.domain.user.LoginResponseDTO;
import com.limadev.cashflow.domain.user.RegisterDTO;
import com.limadev.cashflow.domain.user.User;
import com.limadev.cashflow.exception.BusinessException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("auth")
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Faz o login do usuário", description = "Faz o login do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Faz o login do usuário"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "<ul>" +
            "<li>**__email__**: Email do Usuário.</li>" +
            "<ul>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__password__**: Senha do usuário.</li>" +
            "<ul>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO data) {
        LoginResponseDTO response = userService.login(data);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registra um usuário", description = "Cadastra um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastra o usuário no banco de dados"),
            @ApiResponse(responseCode = "400", description = "Este email já está cadastrado."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "<ul>" +
            "<li>**__email__**: Email do usuário.</li>" +
            "<ul>" +
            "<li>**Email não pode estar em uso**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__name__**: Nome do Usuário.</li>" +
            "<ul>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__password__**: Senha do Usuário.</li>" +
            "<ul>" +
            "<li>**A Senha deve possuir 6 caracteres ou mais.**</li>" +
            "<li>**A Senha deve possuir ao menos 1 número.**</li>" +
            "<li>**A Senha deve possuir ao menos um caractere especial.**</li>" +
            "<li>**A Senha deve possuir ao menos uma letra maiuscula.**</li>" +
            "</ul>" +
            "</ul>" +
            "</ul>")
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO data) throws BusinessException {
        User user = userService.createUser(data);

        return ResponseEntity.status(201).body(user);
    }
}
