package com.example.kotllegacy.repository;

import com.example.kotllegacy.model.entity.JoinRequest;
import com.example.kotllegacy.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findAllByTeamAndStatus(Team team, JoinRequest.Status status);

}
