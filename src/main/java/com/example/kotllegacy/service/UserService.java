package com.example.kotllegacy.service;

import com.example.kotllegacy.model.dto.UserRegistrationDto;
import com.example.kotllegacy.model.entity.User;
import com.example.kotllegacy.model.entity.UserInfo;
import com.example.kotllegacy.repository.UserInfoRepository;
import com.example.kotllegacy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    @Transactional
    public void registrateUser(UserRegistrationDto dto) {
        if (!isValidPosition(dto.getPosition())) {
            throw new IllegalArgumentException("Invalid position specified.");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .role(User.Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        UserInfo userInfo = UserInfo.builder()
                .user(savedUser)
                .fullName(dto.getFullName())
                .birthDate(dto.getBirthDate())
                .description(dto.getDescription())
                .telegramId(dto.getTelegramLink())
                .resumeLink(dto.getResumeLink())
                .position(UserInfo.Position.valueOf(dto.getPosition()))
                .hasTeam(dto.isHasTeam())
                .createdAt(LocalDateTime.now())
                .build();
        userInfoRepository.save(userInfo);
    }

    public boolean authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> password.equals(user.getPassword()))
                .orElse(false);
    }

    private boolean isValidPosition(String position) {
        return switch (position.toUpperCase()) {
            case "BACKEND", "FRONTEND", "ML", "ANALYST", "DEVOPS", "DESIGNER", "SUPPORT", "FULLSTACK" -> true;
            default -> false;
        };
    }
}
