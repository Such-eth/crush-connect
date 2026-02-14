package com.college.crushconnect.dto;

public class MeResponseDto {

    private String email;
    private String name;
    private boolean hasSubmittedCrush;
    private boolean isRevealPhase;
    private boolean hasMatch;

    public MeResponseDto(
            String email,
            String name,
            boolean hasSubmittedCrush,
            boolean isRevealPhase,
            boolean hasMatch) {

        this.email = email;
        this.name = name;
        this.hasSubmittedCrush = hasSubmittedCrush;
        this.isRevealPhase = isRevealPhase;
        this.hasMatch = hasMatch;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
    public boolean isHasSubmittedCrush() { return hasSubmittedCrush; }
    public boolean isRevealPhase() { return isRevealPhase; }
    public boolean isHasMatch() { return hasMatch; }
}
