package com.college.crushconnect.service;

import com.college.crushconnect.entity.Crush;
import com.college.crushconnect.entity.Match;
import com.college.crushconnect.entity.User;
import com.college.crushconnect.repository.CrushRepository;
import com.college.crushconnect.repository.MatchRepository;
import com.college.crushconnect.repository.UserRepository;
import com.college.crushconnect.util.HashUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class CrushService {

    private final CrushRepository crushRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public CrushService(
            CrushRepository crushRepository,
            MatchRepository matchRepository,
            UserRepository userRepository) {
        this.crushRepository = crushRepository;
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void submitCrush(String fromUserEmailId, String targetEmail) {

        User currentUser = userRepository.findByEmail(fromUserEmailId)
                .orElseThrow();
        Long fromUserId = currentUser.getId();

        String normalizedTargetEmail =
                targetEmail.trim().toLowerCase();

        String targetEmailHash =
                HashUtil.sha256(normalizedTargetEmail);

        // 1️⃣ Save crush (idempotent via DB unique constraint)
        Crush crush = new Crush();
        crush.setFromUserId(fromUserId);
        crush.setToEmailHash(targetEmailHash);
        crush.setCreatedAt(Instant.now());

        crushRepository.save(crush);

        // 2️⃣ Check if target user exists
        Optional<User> targetUserOpt =
                userRepository.findByEmail(normalizedTargetEmail);

        if (targetUserOpt.isEmpty()) {
            // Target not registered yet → no match possible now
            return;
        }

        User targetUser = targetUserOpt.get();

        // 3️⃣ Check reverse crush
//        User currentUser = userRepository.findById(fromUserId)
//                .orElseThrow();
        String currentUserEmailHash =
                HashUtil.sha256(currentUser.getEmail().toLowerCase());

        crushRepository.findByFromUserIdAndToEmailHash(
                        targetUser.getId(),
                        currentUserEmailHash
                )
                .ifPresent(reverseCrush ->
                        createMatchSafely(fromUserId, targetUser.getId())
                );
    }

    private void createMatchSafely(Long userA, Long userB) {

        Long min = Math.min(userA, userB);
        Long max = Math.max(userA, userB);

        if (matchRepository.existsByUser1IdAndUser2Id(min, max)) {
            return;
        }

        System.out.println("----------->"+userA+"--"+userB);
        Match match = new Match();
        match.setUser1Id(min);
        match.setUser2Id(max);
        match.setMatchedAt(Instant.now());

        matchRepository.save(match);
    }
}
