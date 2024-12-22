package com.example.kotllegacy.controller;

import com.example.kotllegacy.model.dto.TeamCreationRequest;
import com.example.kotllegacy.model.entity.Team;
import com.example.kotllegacy.model.exception.TeamCreationException;
import com.example.kotllegacy.model.exception.UserAlreadyInTeamException;
import com.example.kotllegacy.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
            log.error("error:", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TeamCreationException e) {
            log.error("error:", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("error:", e);
            return new ResponseEntity<>("Возникла неожиданная ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
