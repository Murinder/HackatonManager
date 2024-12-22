package com.example.kotllegacy.repository;

import com.example.kotllegacy.model.entity.Hackaton;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackatonRepository extends JpaRepository<Hackaton, Long> {
}
