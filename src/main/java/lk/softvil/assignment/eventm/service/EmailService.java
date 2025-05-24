package lk.softvil.assignment.eventm.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendOtpEmail(String email, String otp) {
        // Implementation with SMTP/Mailgun/etc
        System.out.printf("OTP for %s: %s%n", email, otp);
    }
}