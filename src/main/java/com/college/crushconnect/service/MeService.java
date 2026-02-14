package com.college.crushconnect.service;

import com.college.crushconnect.dto.MeResponseDto;
import com.college.crushconnect.entity.User;
import com.college.crushconnect.repository.CrushRepository;
import com.college.crushconnect.repository.MatchRepository;
import com.college.crushconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MeService {

    private final UserRepository userRepository;
    private final CrushRepository crushRepository;
    private final MatchRepository matchRepository;
    private final RevealService revealService;


    public MeService(
            UserRepository userRepository,
            CrushRepository crushRepository,
            MatchRepository matchRepository,
            RevealService revealService) {

        this.userRepository = userRepository;
        this.crushRepository = crushRepository;
        this.matchRepository = matchRepository;
        this.revealService = revealService;
    }

    public MeResponseDto getMe(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();
        Long userId = user.getId();

        boolean submitted =
                crushRepository.existsByFromUserId(userId);

        boolean hasMatch =
                matchRepository.existsByUser1IdOrUser2Id(userId, userId);

        boolean REVEAL_PHASE = revealService.isRevealEnabled();
        return new MeResponseDto(
                user.getEmail(),
                user.getName(),
                submitted,
                REVEAL_PHASE,
                hasMatch
        );
    }
}
