package com.limadev.cashflow.transaction;

import com.limadev.cashflow.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "transactions")
@Entity(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Double amount;
    private String description;
    private String category;
    private TypeDTO type;
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
