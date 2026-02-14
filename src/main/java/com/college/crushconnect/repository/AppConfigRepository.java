package com.college.crushconnect.repository;

import com.college.crushconnect.entity.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppConfigRepository
        extends JpaRepository<AppConfig, String> {
}
