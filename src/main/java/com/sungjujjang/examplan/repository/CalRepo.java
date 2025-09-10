package com.sungjujjang.examplan.repository;

import com.sungjujjang.examplan.entity.CalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalRepo extends JpaRepository<CalEntity, Long> {
    Optional<CalEntity> findByUrlName(String urlName);
    Optional<CalEntity> findByName(String name);
}
