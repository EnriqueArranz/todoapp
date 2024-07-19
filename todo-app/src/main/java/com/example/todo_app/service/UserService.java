package com.example.todo_app.service;

import com.example.todo_app.dto.TodoSummaryDTO;
import com.example.todo_app.model.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();


}
