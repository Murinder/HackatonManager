package com.example.kotllegacy.controller;

import com.example.kotllegacy.model.dto.UserRegistrationDto;
import com.example.kotllegacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private  UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.ok("User registered successfully!");
    }
}
