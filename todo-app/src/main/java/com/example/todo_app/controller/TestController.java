package com.example.todo_app.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return "Authenticated as " + userDetails.getUsername();
        } else {
            return "Not authenticated";
        }
    }
}