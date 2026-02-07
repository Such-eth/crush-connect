package com.college.crushconnect.service;

import com.college.crushconnect.entity.OtpSession;
import com.college.crushconnect.entity.User;
import com.college.crushconnect.exception.*;
import com.college.crushconnect.repository.OtpSessionRepository;
import com.college.crushconnect.repository.UserRepository;
import com.college.crushconnect.util.HashUtil;
import com.college.crushconnect.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OtpVerificationService {

    private final OtpSessionRepository otpSessionRepository;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public String verifyOtp(String email, String otp, String name) {

        OtpSession session = otpSessionRepository
                .findByEmail(email)
                .orElseThrow(() -> new InvalidOtpException("OTP not found"));

        if (session.getIsUsed()) {
            throw new OtpAlreadyUsedException("OTP already used");
        }

        if (session.getExpiresAt().isBefore(Instant.now())) {
            throw new OtpExpiredException("OTP expired");
        }

        String hashedInputOtp = HashUtil.sha256(otp);

        if (!hashedInputOtp.equals(session.getOtpHash())) {
            throw new InvalidOtpException("Invalid OTP");
        }

        // Mark OTP as used (prevents replay)
        session.setIsUsed(true);
        otpSessionRepository.save(session);

        // Create or update user
        User user = userRepository.findByEmail(email)
                .orElse(User.builder()
                        .email(email)
                        .name(name)
                        .createdAt(Instant.now())
                        .build());

        user.setVerified(true);
        userRepository.save(user);

        // JWT generation will be implemented in STEP 3
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        return token;
    }
}

