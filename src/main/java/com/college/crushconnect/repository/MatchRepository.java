package com.college.crushconnect.repository;

import com.college.crushconnect.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    boolean existsByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    List<Match> findByUser1IdOrUser2Id(Long userId1, Long userId2);

    boolean existsByUser1IdOrUser2Id(Long userId, Long userId1);

    List<Match> findAllByUser1IdOrUser2Id(Long u1, Long u2);

}
