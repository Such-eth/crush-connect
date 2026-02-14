package com.college.crushconnect.dto;

import java.util.List;

public class MatchesResponseDto {

    private List<MatchDto> matches;

    public MatchesResponseDto(List<MatchDto> matches) {
        this.matches = matches;
    }

    public List<MatchDto> getMatches() {
        return matches;
    }
}
