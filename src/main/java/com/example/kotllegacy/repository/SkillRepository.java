package com.example.kotllegacy.repository;

import com.example.kotllegacy.model.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findBySkillName(String skillName);
}
