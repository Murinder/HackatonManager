package com.example.kotllegacy.controller;

import com.example.kotllegacy.model.entity.Skill;
import com.example.kotllegacy.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/skills")
@Slf4j
public class SkillController {

    private final SkillService skillService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllSkills() {
        try {
            return ResponseEntity.ok((skillService.getAllSkills()));
        } catch (Exception ex) {
            log.error("Неожиданная ошибка: ", ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/{userId}/add")
    public void addSkillsToUser(@PathVariable Long userId, @RequestBody List<String> skillNames) {
        try {
            skillService.addSkillsToUser(userId, skillNames);
        } catch (Exception ex) {
            log.error("Неожиданная ошибка: ", ex);
        }
    }
}
