package com.example.kotllegacy.controller;

import com.example.kotllegacy.model.dto.TeamCreationRequest;
import com.example.kotllegacy.model.entity.Team;
import com.example.kotllegacy.model.exception.TeamException.TeamCreationException;
import com.example.kotllegacy.model.exception.TeamException.TeamNotFoundException;
import com.example.kotllegacy.model.exception.UserException.UserAlreadyInTeamException;
import com.example.kotllegacy.model.exception.UserException.UserNotFoundException;
import com.example.kotllegacy.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/create/{id}")
    public ResponseEntity<?> createTeam(@RequestBody TeamCreationRequest request, @PathVariable Long id) {
        try {
            Team team = teamService.createTeam(request, id);
            return new ResponseEntity<>(team, HttpStatus.CREATED);
        } catch (UserAlreadyInTeamException e) {
            log.error("Ошибка при создании команды:", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TeamCreationException e) {
            log.error("Ошибка при создании команды:", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Возникла неожиданная ошибка:", e);
            return new ResponseEntity<>("Возникла неожиданная ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{teamId}/addMember/{userId}")
    public void addMember(@PathVariable Long teamId, @PathVariable Long userId) {
        try {
            teamService.addMember(teamId, userId);
        } catch (UserNotFoundException ex) {
            log.error("Ошибка при нахождении пользователя:", ex.getMessage());
        } catch (TeamNotFoundException ex) {
            log.error("Ошибка при нахождении команды:", ex.getMessage());
        } catch (Exception ex) {
            log.error("Неожиданная ошибка:", ex.getMessage());
        }
    }

    @PostMapping("/{teamId}/removeMember/{userId}")
    public void removeMember(@PathVariable Long teamId, @PathVariable Long userId) {
        try {
            teamService.removeMember(teamId, userId);
        } catch (UserNotFoundException ex) {
            log.error("Ошибка при нахождении пользователя:", ex.getMessage());
        } catch (TeamNotFoundException ex) {
            log.error("Ошибка при нахождении команды:", ex.getMessage());
        } catch (Exception ex) {
            log.error("Неожиданная ошибка:", ex.getMessage());
        }
    }

    @PostMapping("/{teamId}/updateDescription")
    public void updateTeamDescription(@PathVariable Long teamId, @RequestParam String newDescription) {
        try {
            teamService.updateTeamDescription(teamId, newDescription);
        } catch (TeamNotFoundException ex) {
            log.error("Ошибка при нахождении команды:", ex.getMessage());
        } catch (Exception ex) {
            log.error("Неожиданная ошибка:", ex.getMessage());
        }
    }

    @PostMapping("/{teamId}/transferLeadership/{newLeaderId}")
    public void transferCaptaincy(@PathVariable Long teamId, @PathVariable Long newLeaderId) {
        try {
            teamService.transferLeader(teamId, newLeaderId);
        } catch (UserNotFoundException ex) {
            log.error("Ошибка при нахождении пользователя:", ex.getMessage());
        } catch (TeamNotFoundException ex) {
            log.error("Ошибка при нахождении команды:", ex.getMessage());
        } catch (Exception ex) {
            log.error("Неожиданная ошибка:", ex.getMessage());
        }
    }
}
