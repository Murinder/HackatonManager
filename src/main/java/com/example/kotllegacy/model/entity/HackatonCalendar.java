package com.example.kotllegacy.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hackaton_calendar")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class HackatonCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hackaton_id", nullable = false)
    private Hackaton hackaton;
}
