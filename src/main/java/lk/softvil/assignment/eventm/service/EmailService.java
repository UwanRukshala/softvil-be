package lk.softvil.assignment.eventm.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendOtpEmail(String email, String otp) {

        System.out.printf("OTP for %s: %s%n", email, otp);
    }
}