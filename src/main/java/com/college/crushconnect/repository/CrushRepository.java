package com.college.crushconnect.repository;

import com.college.crushconnect.entity.Crush;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrushRepository extends JpaRepository<Crush, Long> {

    boolean existsByFromUserId(Long fromUserId);

    Optional<Crush> findByFromUserId(Long fromUserId);

    Optional<Crush> findByToEmailHash(String toEmailHash);

    Optional<Crush> findByFromUserIdAndToEmailHash(
            Long fromUserId,
            String toEmailHash
    );
}
