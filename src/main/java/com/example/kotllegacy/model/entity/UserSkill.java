package com.example.kotllegacy.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_skills")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}
