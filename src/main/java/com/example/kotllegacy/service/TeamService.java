package com.example.kotllegacy.service;

import com.example.kotllegacy.model.dto.TeamCreationRequest;
import com.example.kotllegacy.model.entity.Team;
import com.example.kotllegacy.model.entity.TeamMember;
import com.example.kotllegacy.model.entity.User;
import com.example.kotllegacy.model.exception.TeamCreationException;
import com.example.kotllegacy.model.exception.UserAlreadyInTeamException;
import com.example.kotllegacy.repository.TeamMemberRepository;
import com.example.kotllegacy.repository.TeamRepository;
import com.example.kotllegacy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static jdk.dynalink.linker.support.Guards.isNull;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final UserRepository userRepository;

    @Transactional
    public Team createTeam(TeamCreationRequest request, Long userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("Пользователь не найден");
        }

        var userDb = user.get();

        if (userDb.getUserInfo().getHasTeam()) {
            throw new UserAlreadyInTeamException("Пользователь уже в команде");
        }

        Team team = Team.builder()
                .teamName(request.getTeamName())
                .description(request.getDescription())
                .leader(userDb)
                .createdAt(LocalDateTime.now())
                .build();
        try{
            team = teamRepository.save(team);
        }
        catch(Exception ex){
            throw new TeamCreationException(ex.getMessage());
        }

        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .user(userDb)
                .isLeader(true)
                .joinedAt(LocalDateTime.now())
                .build();

        teamMemberRepository.save(teamMember);

        userDb.getUserInfo().setHasTeam(true);
        userRepository.save(userDb);

        return team;
    }
}
