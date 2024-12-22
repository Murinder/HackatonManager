package com.example.kotllegacy.repository;

import com.example.kotllegacy.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
