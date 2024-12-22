package com.example.kotllegacy.controller;

import com.example.kotllegacy.model.dto.UserRegistrationDto;
import com.example.kotllegacy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public String registerUser(@RequestBody UserRegistrationDto dto) {
        try {
            userService.registrateUser(dto);
            return "User registered successfully!";
        } catch (Exception ex) {
            log.error("Ошибка при регистрации:", ex);
            return "Registration Failed";
        }
    }
}
