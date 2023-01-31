package com.example.BlogWebApp.controllers;

import com.example.BlogWebApp.auth.Role;
import com.example.BlogWebApp.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
public interface UserController {
    @Role("admin")
    @GetMapping
    List<User> getAllUsers();

    @Role("admin")
    @GetMapping("/{username}")
    User getUserByUsername(@PathVariable String username) throws JsonProcessingException;

    @PostMapping("/register")
    User addUser(@RequestBody User user);

    @Role("admin")
    @PutMapping("/{username}")
    Object updateUser(@RequestBody User user, @PathVariable String username) throws JsonProcessingException;

    @Role("admin")
    @DeleteMapping("/{username}")
    @ResponseBody
    Object deleteUser(@PathVariable String username) throws JsonProcessingException;
}
