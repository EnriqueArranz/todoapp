package com.example.todo_app.service.impl;

import com.example.todo_app.dto.AuthenticationResponse;
import com.example.todo_app.dto.LogInRequest;
import com.example.todo_app.dto.RegistrationRequest;
import com.example.todo_app.model.domain.User;
import com.example.todo_app.model.enums.Role;
import com.example.todo_app.repository.UserRepository;
import com.example.todo_app.service.AuthenticationService;
import com.example.todo_app.security.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("This username is already taken");
        }
        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .street(request.getStreet())
                .city(request.getCity())
                .zipcode(request.getZipcode())
                .country(request.getCountry())
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public AuthenticationResponse authenticate(LogInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }
}

