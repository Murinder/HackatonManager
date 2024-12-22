package com.example.kotllegacy.repository;

import com.example.kotllegacy.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByTeamName(String teamName);
}
