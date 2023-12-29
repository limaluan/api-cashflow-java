package com.limadev.cashflow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limadev.cashflow.repositories.UserRepository;
import com.limadev.cashflow.services.TokenService;
import com.limadev.cashflow.user.User;
import com.limadev.cashflow.user.UserDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository repository;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        var sub = tokenService.validateToken(token);
        User user = (User) repository.findByEmail(sub);
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getName());

        return ResponseEntity.ok(userDTO);
    }
}
