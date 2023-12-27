package com.limadev.cashflow.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String helloWord() {
        return "Hello World!";
    }

    @PostMapping
    public String post() {
        return "Este foi o post";
    }
}
