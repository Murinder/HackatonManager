package com.example.kotllegacy.repository;

import com.example.kotllegacy.model.entity.TeamHackaton;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamProjectRepository extends JpaRepository<TeamHackaton, Long> {
}
