package com.example.BlogWebApp.controllers;

import com.example.BlogWebApp.entities.*;
import com.example.BlogWebApp.exceptions.NotFoundException;
import com.example.BlogWebApp.mappers.UserMapper;
import com.example.BlogWebApp.utility.PasswordEncryptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.BlogWebApp.entities.ErrorResponse.NO_USER_MESSAGE;

@RestController
public class UserControllerImpl implements UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    public User getUserByUsername(String username) throws JsonProcessingException {
        User user = userMapper.getUser(username);
        if (user == null)
            throwUserException(username, "");
        return user;
    }

    public User addUser(User user) {
        user.password = PasswordEncryptor.encryptPassword(user.password + user.generateSalt());
        userMapper.insertUser(user);
        return user;
    }

    public Object updateUser(User user, String username) throws JsonProcessingException {
        user.username = username;
        int affectedRows = userMapper.updateUser(user);
        if (affectedRows != 1)
            throwUserException(username, " was updated");
        return user;
    }

    public Object deleteUser(String username) throws JsonProcessingException {
        int affectedRows = userMapper.deleteUser(username);
        if (affectedRows != 1)
            throwUserException(username, " was deleted");
        return username;
    }

    private void throwUserException(String username, String msg) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), NO_USER_MESSAGE + username + msg);
        String jsonError = objectMapper.writeValueAsString(errorResponse);
        throw new NotFoundException(jsonError);
    }
}
