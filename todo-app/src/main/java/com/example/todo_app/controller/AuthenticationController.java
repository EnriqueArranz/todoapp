package com.example.todo_app.controller;

import com.example.todo_app.dto.AuthenticationResponse;
import com.example.todo_app.dto.LogInRequest;
import com.example.todo_app.dto.RegistrationRequest;
import com.example.todo_app.service.AuthenticationService;
import com.example.todo_app.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final TodoService todoService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LogInRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}