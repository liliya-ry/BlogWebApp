package com.example.BlogWebApp.exceptions;

public class PostNotFoundException extends RuntimeException {
    private final String message;

    public PostNotFoundException(String jsonMessage) {
        message = jsonMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
