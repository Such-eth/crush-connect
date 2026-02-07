package com.college.crushconnect.controller;

import com.college.crushconnect.dto.RequestOtpRequest;
import com.college.crushconnect.dto.VerifyOtpRequest;
import com.college.crushconnect.dto.VerifyOtpResponse;
import com.college.crushconnect.service.OtpService;
import com.college.crushconnect.service.OtpVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OtpService otpService;
    private final OtpVerificationService otpVerificationService;

    @PostMapping("/request-otp")
    public ResponseEntity<Map<String, String>> requestOtp(
            @RequestBody @Valid RequestOtpRequest request) {

        otpService.sendOtp(request.getEmail());

        // Generic response to avoid email enumeration
        return ResponseEntity.ok(
                Map.of("message", "OTP sent if email exists")
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<VerifyOtpResponse> verifyOtp(
            @RequestBody VerifyOtpRequest request) {

        String token = otpVerificationService.verifyOtp(
                request.getEmail(),
                request.getOtp(),
                request.getName()
        );

        return ResponseEntity.ok(new VerifyOtpResponse(token));
    }
}

