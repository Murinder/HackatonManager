package com.example.kotllegacy.controller;

import com.example.kotllegacy.model.dto.LoginRequest;
import com.example.kotllegacy.model.dto.UpdateUserInfoDto;
import com.example.kotllegacy.model.dto.UserRegistrationDto;
import com.example.kotllegacy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(401).body("failure");
        }
    }

    @PutMapping("/{userId}/info")
    public ResponseEntity<?> updateUserInfo(@PathVariable Long userId, @RequestBody UpdateUserInfoDto updateDto) {
        try {
            userService.updateUserInfo(userId, updateDto);
            return ResponseEntity.ok("Информация пользователя успешно обновлена!");
        } catch (Exception ex) {
            log.error("Ошибка при обновлении информации пользователя:", ex);
            return ResponseEntity.status(500).body("Не удалось обновить информацию пользователя.");
        }
    }
}