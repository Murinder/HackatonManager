package com.example.kotllegacy.service;

import com.example.kotllegacy.model.dto.UserRegistrationDto;
import com.example.kotllegacy.model.entity.User;
import com.example.kotllegacy.model.entity.UserInfo;
import com.example.kotllegacy.repository.UserInfoRepository;
import com.example.kotllegacy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
//    private PasswordEncoder passwordEncoder;


    @Transactional
    public void registerUser(UserRegistrationDto dto) {
        // Validate position
        if (!isValidPosition(dto.getPosition())) {
            throw new IllegalArgumentException("Invalid position specified.");
        }

        // Create and save User entity
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setNickname(dto.getNickname());
//        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPassword(dto.getPassword());
        user.setRole(User.Role.USER);
        User savedUser = userRepository.save(user);

        // Create and save UserInfo entity
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(savedUser);
        userInfo.setFullName(dto.getFullName());
        userInfo.setBirthDate(dto.getBirthDate());
        userInfo.setDescription(dto.getDescription());
        userInfo.setTelegramId(dto.getTelegramLink());
        userInfo.setResumeLink(dto.getResumeLink());
        userInfo.setPosition(UserInfo.Position.valueOf(dto.getPosition()));
        userInfo.setHasTeam(dto.isHasTeam());
        userInfoRepository.save(userInfo);
    }

    private boolean isValidPosition(String position) {
        return switch (position.toUpperCase()) {
            case "BACKEND", "FRONTEND", "ML", "ANALYST", "DEVOPS", "DESIGNER", "SUPPORT", "FULLSTACK" -> true;
            default -> false;
        };
    }
}
