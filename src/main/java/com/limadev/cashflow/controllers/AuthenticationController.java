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

@RestController
@RequestMapping("auth")
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO data) {
        LoginResponseDTO response = userService.login(data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO data) throws BusinessException {
        User user = userService.createUser(data);

        return ResponseEntity.ok(user);
    }
}
