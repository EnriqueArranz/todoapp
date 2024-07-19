package com.example.todo_app.service.impl;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message)
    {
        super(message);
    }
}
