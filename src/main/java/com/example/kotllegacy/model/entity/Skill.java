package com.example.kotllegacy.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skills")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "skill_name",nullable = false, unique = true)
    private String skillName;
}
