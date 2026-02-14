package com.college.crushconnect.service;

import com.college.crushconnect.dto.MatchDto;
import com.college.crushconnect.dto.MatchesResponseDto;
import com.college.crushconnect.entity.Match;
import com.college.crushconnect.entity.User;
import com.college.crushconnect.repository.MatchRepository;
import com.college.crushconnect.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final RevealService revealService;

    public MatchService(
            MatchRepository matchRepository,
            UserRepository userRepository,
            RevealService revealService) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.revealService = revealService;
    }

    public MatchesResponseDto getMatches(String userMail) {

        boolean REVEAL_PHASE = revealService.isRevealEnabled();
        if (!REVEAL_PHASE) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Reveal phase not active"
            );
        }

        User user = userRepository.findByEmail(userMail).get();
        Long userId = user.getId();

        List<Match> matches =
                matchRepository.findAllByUser1IdOrUser2Id(
                        userId,
                        userId
                );

        List<MatchDto> result = new ArrayList<>();

        for (Match m : matches) {

            Long otherId =
                    m.getUser1Id().equals(userId)
                            ? m.getUser2Id()
                            : m.getUser1Id();

            User other = userRepository
                    .findById(otherId)
                    .orElseThrow();

            result.add(
                    new MatchDto(
                            other.getName(),
                            other.getEmail(),
                            m.getMatchedAt()
                    )
            );
        }

        return new MatchesResponseDto(result);
    }
}
