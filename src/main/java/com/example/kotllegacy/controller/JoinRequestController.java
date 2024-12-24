package com.example.kotllegacy.controller;

import com.example.kotllegacy.model.dto.UpdateUserInfoDto;
import com.example.kotllegacy.model.entity.JoinRequest;
import com.example.kotllegacy.service.JoinRequestService;
import com.example.kotllegacy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/join-requests")
@RequiredArgsConstructor
@Slf4j
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    private final UserService userService;

    @PostMapping("/send/{teamId}/{userId}")
    public ResponseEntity<?> sendJoinRequest(@PathVariable Long teamId, @PathVariable Long userId) {
        try {
            joinRequestService.createJoinRequest(teamId, userId);
            log.info("Запрос на присоединение отправлен: teamId={}, userId={}", teamId, userId);
            return ResponseEntity.ok("Запрос на присоединение успешно отправлен.");
        } catch (Exception e) {
            log.error("Не удалось отправить запрос на присоединение: teamId={}, userId={}, ошибка={}", teamId, userId, e.getMessage());
            return ResponseEntity.badRequest().body("Не удалось отправить запрос на присоединение: " + e.getMessage());
        }
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> getTeamJoinRequests(@PathVariable Long teamId) {
        try {
            List<JoinRequest> requests = joinRequestService.getTeamJoinRequests(teamId);
            log.info("Запросы на присоединение для команды teamId={} успешно получены", teamId);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            log.error("Не удалось получить запросы на присоединение: teamId={}, ошибка={}", teamId, e.getMessage());
            return ResponseEntity.badRequest().body("Не удалось получить запросы на присоединение: " + e.getMessage());
        }
    }

    @PostMapping("/{requestId}/handle")
    public ResponseEntity<?> handleJoinRequest(@PathVariable Long requestId, @RequestParam boolean isApproved) {
        try {
            joinRequestService.handleJoinRequest(requestId, isApproved);
            String action = isApproved ? "одобрен" : "отклонен";
            log.info("Запрос на присоединение requestId={} успешно {}", requestId, action);
            return ResponseEntity.ok("Запрос на присоединение успешно " + action + ".");
        } catch (Exception e) {
            log.error("Не удалось обработать запрос на присоединение: requestId={}, isApproved={}, ошибка={}", requestId, isApproved, e.getMessage());
            return ResponseEntity.badRequest().body("Не удалось обработать запрос на присоединение: " + e.getMessage());
        }
    }

    @PutMapping("/{userId}/info")
    public ResponseEntity<?> updateUserInfo(@PathVariable Long userId, @RequestBody UpdateUserInfoDto updateDto) {
        try {
            userService.updateUserInfo(userId, updateDto);
            return ResponseEntity.ok("Информация пользователя успешно обновлена!");
        } catch (Exception ex) {
            log.error("Ошибка при обновлении информации пользователя:", ex);
            return ResponseEntity.status(500).body("Не удалось обновить информацию пользователя.");
        }
    }

}
