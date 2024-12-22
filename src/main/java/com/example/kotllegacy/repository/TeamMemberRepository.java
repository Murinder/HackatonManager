package com.example.kotllegacy.repository;

import com.example.kotllegacy.model.entity.Team;
import com.example.kotllegacy.model.entity.TeamMember;
import com.example.kotllegacy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findByTeamAndUser(Team team, User newLeader);
}
