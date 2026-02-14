package com.college.crushconnect.controller;

import com.college.crushconnect.dto.MatchesResponseDto;
import com.college.crushconnect.service.MatchService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/matches")
    public MatchesResponseDto getMatches(
            Authentication authentication) {

        String  userEmail = authentication.getName();

        return matchService.getMatches(userEmail);
    }
}
