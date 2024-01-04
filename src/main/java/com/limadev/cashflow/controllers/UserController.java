package com.limadev.cashflow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limadev.cashflow.domain.repositories.UserRepository;
import com.limadev.cashflow.domain.services.TokenService;
import com.limadev.cashflow.domain.user.User;
import com.limadev.cashflow.domain.user.UserDTO;
import com.limadev.cashflow.domain.user.UserDTOMapper;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository repository;

    @Autowired
    UserDTOMapper userDTOMapper;
    
    @GetMapping
    public ResponseEntity<UserDTO> getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        var sub = tokenService.validateToken(token);
        User user = (User) repository.findByEmail(sub);
        UserDTO userDTO = userDTOMapper.apply(user);

        return ResponseEntity.ok(userDTO);
    }
}
