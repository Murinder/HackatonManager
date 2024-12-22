package com.example.kotllegacy.repository;

import com.example.kotllegacy.model.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}
