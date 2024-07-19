package com.example.todo_app.service;

import com.example.todo_app.dto.AuthenticationResponse;
import com.example.todo_app.dto.LogInRequest;
import com.example.todo_app.dto.RegistrationRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegistrationRequest request);
    AuthenticationResponse authenticate(LogInRequest request);
}
