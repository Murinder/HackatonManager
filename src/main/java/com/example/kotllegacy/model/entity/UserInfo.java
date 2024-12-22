package com.example.kotllegacy.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_info")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    private String description;

    @Column(name = "telegram_id")
    private String telegramId;

    @Column(name = "resume_link")
    private String resumeLink;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "has_team")
    private Boolean hasTeam = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Position {
        BACKEND,
        FRONTEND,
        ML,
        ANALYST,
        DEVOPS,
        DESIGNER,
        SUPPORT,
        FULLSTACK
    }
}
