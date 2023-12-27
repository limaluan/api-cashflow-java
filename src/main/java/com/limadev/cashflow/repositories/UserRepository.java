package com.limadev.cashflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.limadev.cashflow.user.User;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByEmail(String email);
}
