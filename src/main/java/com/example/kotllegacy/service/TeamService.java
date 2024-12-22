package com.example.kotllegacy.service;

import com.example.kotllegacy.model.dto.TeamCreationRequest;
import com.example.kotllegacy.model.entity.Team;
import com.example.kotllegacy.model.entity.TeamMember;
import com.example.kotllegacy.model.exception.TeamException.TeamCreationException;
import com.example.kotllegacy.model.exception.TeamException.TeamNotFoundException;
import com.example.kotllegacy.model.exception.UserException.UserAlreadyInTeamException;
import com.example.kotllegacy.model.exception.UserException.UserNotFoundException;
import com.example.kotllegacy.repository.TeamMemberRepository;
import com.example.kotllegacy.repository.TeamRepository;
import com.example.kotllegacy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final UserRepository userRepository;

    @Transactional
    public Team createTeam(TeamCreationRequest request, Long userId) {
        var user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Команда не найдена"));

        if (user.getUserInfo().getHasTeam()) {
            throw new UserAlreadyInTeamException("Пользователь уже в команде");
        }

        Team team = Team.builder()
                .teamName(request.getTeamName())
                .description(request.getDescription())
                .leader(user)
                .createdAt(LocalDateTime.now())
                .build();
        try {
            team = teamRepository.save(team);
        } catch (Exception ex) {
            throw new TeamCreationException(ex.getMessage());
        }

        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .user(user)
                .isLeader(true)
                .joinedAt(LocalDateTime.now())
                .build();

        teamMemberRepository.save(teamMember);

        user.getUserInfo().setHasTeam(true);
        userRepository.save(user);

        return team;
    }

    @Transactional
    public void addMember(Long teamId, Long userId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Команда не найдена"));

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        if (user.getUserInfo().getHasTeam()) {
            throw new UserAlreadyInTeamException("Пользователь уже в команде");
        }

        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .user(user)
                .isLeader(false)
                .joinedAt(LocalDateTime.now())
                .build();

        teamMemberRepository.save(teamMember);
        user.getUserInfo().setHasTeam(true);
        userRepository.save(user);
    }

    @Transactional
    public void removeMember(Long teamId, Long userId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Команда не найдена"));

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        TeamMember teamMember = teamMemberRepository.findByTeamAndUser(team, user)
                .orElseThrow(() -> new UserNotFoundException("Участник не найден"));

        if (Objects.equals(team.getLeader().getUserId(), userId)) {
            throw new RuntimeException("Нельзя удалить капитана команды пока не переданы права");
        }

        teamMemberRepository.delete(teamMember);
        user.getUserInfo().setHasTeam(false);
        userRepository.save(user);
    }

    @Transactional
    public void updateTeamDescription(Long teamId, String newDescription) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Команда не найдена"));

        team.setDescription(newDescription);
        teamRepository.save(team);
    }

    @Transactional
    public void transferLeader(Long teamId, Long newLeaderId) {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Команда не найдена"));

        var newLeader = userRepository.findById(newLeaderId)
                .orElseThrow(() -> new UserNotFoundException("Новый лидер не найден"));

        if (!team.getLeader().equals(newLeader)) {
            var currentLeader = team.getLeader();
            TeamMember newLeaderMember = teamMemberRepository.findByTeamAndUser(team, newLeader)
                    .orElseThrow(() -> new RuntimeException("Новый лидер не является участником команды"));

            newLeaderMember.setIsLeader(true);
            teamMemberRepository.save(newLeaderMember);

            TeamMember currentLeaderMember = teamMemberRepository.findByTeamAndUser(team, currentLeader)
                    .orElseThrow(() -> new UserNotFoundException("Текущий лидер не найден"));

            currentLeaderMember.setIsLeader(false);
            teamMemberRepository.save(currentLeaderMember);

            team.setLeader(newLeader);
            teamRepository.save(team);
        }
    }
}
