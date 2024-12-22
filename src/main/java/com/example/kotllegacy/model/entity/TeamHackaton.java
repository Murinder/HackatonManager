package com.example.kotllegacy.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_hackatons")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TeamHackaton {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "hackaton_id", nullable = false)
    private Hackaton hackaton;

    @Column(name = "submitted_at", nullable = false, updatable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();
}
