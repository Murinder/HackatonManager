package com.example.kotllegacy.service;

import com.example.kotllegacy.model.entity.*;
import com.example.kotllegacy.model.entity.JoinRequest.Status;
import com.example.kotllegacy.model.exception.TeamException.TeamNotFoundException;
import com.example.kotllegacy.model.exception.UserException.UserNotFoundException;
import com.example.kotllegacy.repository.JoinRequestRepository;
import com.example.kotllegacy.repository.TeamRepository;
import com.example.kotllegacy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamService teamService;

    @Transactional
    public void createJoinRequest(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Команда не найдена"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        JoinRequest joinRequest = JoinRequest.builder()
                .team(team)
                .user(user)
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        joinRequestRepository.save(joinRequest);
    }

    public List<JoinRequest> getTeamJoinRequests(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Команда не найдена"));

        return joinRequestRepository.findAllByTeamAndStatus(team, Status.PENDING);
    }

    @Transactional
    public void handleJoinRequest(Long requestId, boolean isApproved) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Запрос не найден"));

        if (isApproved) {
            joinRequest.setStatus(Status.APPROVED);
            teamService.addMember(joinRequest.getTeam().getTeamId(), joinRequest.getUser().getUserId());
        } else {
            joinRequest.setStatus(Status.REJECTED);
        }

        joinRequestRepository.save(joinRequest);
    }
}
