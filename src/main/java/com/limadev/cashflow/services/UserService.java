package com.limadev.cashflow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limadev.cashflow.repositories.UserRepository;
import com.limadev.cashflow.user.User;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;
    
    public User getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        String sub = tokenService.validateToken(token);
        User user = (User) userRepository.findByEmail(sub);
        return user;
    }
}
