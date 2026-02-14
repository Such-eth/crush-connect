package com.college.crushconnect.controller;

import com.college.crushconnect.dto.CrushRequestDto;
import com.college.crushconnect.entity.User;
import com.college.crushconnect.service.CrushService;
import com.college.crushconnect.service.OtpVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crush")
public class CrushController {

    private final CrushService crushService;
    private final OtpVerificationService otpVerificationService;

    public CrushController(CrushService crushService, OtpVerificationService otpVerificationService) {
        this.crushService = crushService;
        this.otpVerificationService = otpVerificationService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> submitCrush(
            @RequestBody CrushRequestDto request,
            Authentication authentication) {
        String username = authentication.getName();

        crushService.submitCrush(username, request.getTargetEmail());

        return ResponseEntity.ok(
                Map.of("message", "Your selection has been saved")
        );
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(otpVerificationService.getAllUsers());
    }
}
