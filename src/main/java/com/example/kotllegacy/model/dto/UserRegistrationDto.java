package com.example.kotllegacy.model.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserRegistrationDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    @NotBlank
    private String fullName;

    @NotNull
    private LocalDate birthDate;

    private String description;

    private String telegramLink;

    private String resumeLink;

    @NotBlank
    private String position;

    private boolean hasTeam;
}
