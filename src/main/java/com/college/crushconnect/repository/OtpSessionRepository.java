package com.college.crushconnect.repository;

import com.college.crushconnect.entity.OtpSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpSessionRepository extends JpaRepository<OtpSession, Long> {

    Optional<OtpSession> findTopByEmailAndIsUsedFalseOrderByCreatedAtDesc(String email);
    Optional<OtpSession> findByEmail(String email);
}
