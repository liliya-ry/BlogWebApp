package com.example.BlogWebApp.controllers;

import com.example.BlogWebApp.entities.*;
import com.example.BlogWebApp.exceptions.NotFoundException;
import com.example.BlogWebApp.mappers.UserMapper;
import com.example.BlogWebApp.auth.PasswordEncryptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.example.BlogWebApp.entities.ErrorResponse.NO_USER_MESSAGE;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;

    public User registerUser(User user) {
        user.password = PasswordEncryptor.encryptPassword(user.password + user.generateSalt());
        userMapper.insertUser(user);
        return user;
    }

    public User getUserByUsername(String username) throws JsonProcessingException {
        User user = userMapper.getUser(username);
        if (user == null)
            throwUserException(username);
        return user;
    }

    private void throwUserException(String username) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), NO_USER_MESSAGE + username);
        String jsonError = objectMapper.writeValueAsString(errorResponse);
        throw new NotFoundException(jsonError);
    }
}
