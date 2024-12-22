package com.example.kotllegacy.repository;

import com.example.kotllegacy.model.entity.HackatonCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackatonCalendarRepository extends JpaRepository<HackatonCalendar, Long> {
}
