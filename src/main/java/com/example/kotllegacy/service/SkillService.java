package com.example.kotllegacy.service;

import com.example.kotllegacy.model.entity.Skill;
import com.example.kotllegacy.model.entity.User;
import com.example.kotllegacy.model.entity.UserSkill;
import com.example.kotllegacy.repository.SkillRepository;
import com.example.kotllegacy.repository.UserRepository;
import com.example.kotllegacy.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public void addSkillsToUser(Long userId, List<String> skillNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        for (String skillName : skillNames) {
            Skill skill = skillRepository.findBySkillName(skillName)
                    .orElseThrow(() -> new RuntimeException("Навык не найден: " + skillName));

            UserSkill userSkill = UserSkill.builder()
                    .user(user)
                    .skill(skill)
                    .build();

            userSkillRepository.save(userSkill);
        }
    }
}
