package com.college.crushconnect.controller;

import com.college.crushconnect.dto.MeResponseDto;
import com.college.crushconnect.service.MeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

    private final MeService meService;

    public MeController(MeService meService) {
        this.meService = meService;
    }

    @GetMapping("/me")
    public MeResponseDto me(Authentication authentication) {

        String username = authentication.getName();

        return meService.getMe(username);
    }
}
