package com.example.BlogWebApp.controllers;

import com.example.BlogWebApp.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public interface UserController {
    @PostMapping("/register")
    User registerUser(@RequestBody User user);

    @GetMapping
    User getUserByUsername(@RequestParam String username) throws JsonProcessingException;
}
