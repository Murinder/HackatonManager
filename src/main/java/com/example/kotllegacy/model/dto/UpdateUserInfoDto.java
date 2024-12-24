package com.example.kotllegacy.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserInfoDto {
    private String fullName;
    private LocalDate birthDate;
    private String description;
    private String telegramId;
    private String resumeLink;
    private String position;
}
