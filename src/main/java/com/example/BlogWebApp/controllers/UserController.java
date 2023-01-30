package com.example.BlogWebApp.controllers;

import com.example.BlogWebApp.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
public interface UserController {
    @GetMapping
    List<User> getAllUsers();

    @GetMapping("/{username}")
    User getUserByUsername(@PathVariable String username) throws JsonProcessingException;

    @PostMapping
    User addUser(@RequestBody User user);

    @PutMapping("/{username}")
    Object updateUser(@RequestBody User user, @PathVariable String username) throws JsonProcessingException;

    @DeleteMapping("/{username}")
    @ResponseBody
    Object deleteUser(@PathVariable String username) throws JsonProcessingException;
}
