package com.example.BlogWebApp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class User {
    private static final Random RANDOM = new Random();
    public int id;
    public String username;
    public String password;
    public String email;
    public String firstName;
    public String lastName;
    public int salt;

    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("email") String email,
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int generateSalt() {
        return this.salt = RANDOM.nextInt();
    }
}