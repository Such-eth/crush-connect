package com.college.crushconnect.service;

import com.college.crushconnect.entity.OtpSession;
import com.college.crushconnect.repository.OtpSessionRepository;
import com.college.crushconnect.util.HashUtil;
import com.college.crushconnect.util.OtpGenerator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class OtpService {

    private final OtpSessionRepository otpRepository;
    private final JavaMailSender mailSender;

    public OtpService(OtpSessionRepository otpRepository,
                      BrevoEmailService brevoEmailService) {
        this.otpRepository = otpRepository;
        this.brevoEmailService = brevoEmailService;
    }

    public void sendOtp(String email) {

        String otp = OtpGenerator.generate();
        String otpHash = HashUtil.sha256(otp);

        OtpSession session = new OtpSession();
        session.setEmail(email);
        session.setOtpHash(otpHash);
        session.setExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES));
        session.setIsUsed(false);

        otpRepository.save(session);

        sendEmail(email, otp);
    }

    private void sendEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Login OTP");
        message.setText(
                "Your OTP is: " + otp +
                        "\nThis OTP is valid for 5 minutes.\n\n" +
                        "If you did not request this, please ignore."
        );

        mailSender.send(message);
    }
}
