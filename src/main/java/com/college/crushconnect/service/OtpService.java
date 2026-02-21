package com.college.crushconnect.service;

import com.college.crushconnect.entity.OtpSession;
import com.college.crushconnect.repository.OtpSessionRepository;
import com.college.crushconnect.util.HashUtil;
import com.college.crushconnect.util.OtpGenerator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class OtpService {

    private final OtpSessionRepository otpRepository;
    private BrevoEmailService brevoEmailService;

    public OtpService(OtpSessionRepository otpRepository,
                      BrevoEmailService brevoEmailService) {
        this.otpRepository = otpRepository;
        this.brevoEmailService = brevoEmailService;
    }

    public void sendOtp(String email) {

        String otp = OtpGenerator.generate();
        String otpHash = HashUtil.sha256(otp);

        OtpSession session = otpRepository.findByEmail(email)
                .orElse(new OtpSession());
        session.setEmail(email);
        session.setOtpHash(otpHash);
        session.setExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES));
        session.setIsUsed(false);

        otpRepository.save(session);

        sendEmail(email, otp);
    }

    private void sendEmail(String to, String otp) {
        brevoEmailService.sendOtpEmail(to, otp);
    }
}
