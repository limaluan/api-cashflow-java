package com.limadev.cashflow.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.limadev.cashflow.domain.repositories.UserRepository;
import com.limadev.cashflow.domain.user.AuthenticationDTO;
import com.limadev.cashflow.domain.user.LoginResponseDTO;
import com.limadev.cashflow.domain.user.RegisterDTO;
import com.limadev.cashflow.domain.user.User;
import com.limadev.cashflow.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    public User getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        String sub = tokenService.validateToken(token);
        User user = (User) userRepository.findByEmail(sub);
        return user;
    }

    public User createUser(RegisterDTO data) throws BusinessException {
        if (this.userRepository.findByEmail(data.email()) != null)
            throw new BusinessException("Este email já está cadastrado.");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), data.name(), encryptedPassword);

        userRepository.save(newUser);

        return newUser;
    }

    public LoginResponseDTO login(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new LoginResponseDTO(token);
    }
}
