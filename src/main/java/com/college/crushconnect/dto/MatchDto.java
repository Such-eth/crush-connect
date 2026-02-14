package com.college.crushconnect.dto;

import java.time.Instant;

public class MatchDto {

    private String name;
    private String email;
    private Instant matchedAt;

    public MatchDto(String name, String email, Instant matchedAt) {
        this.name = name;
        this.email = email;
        this.matchedAt = matchedAt;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public Instant getMatchedAt() { return matchedAt; }
}
