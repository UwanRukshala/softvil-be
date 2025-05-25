package lk.softvil.assignment.eventm.security.service;


import lk.softvil.assignment.eventm.exception.InvalidOtpException;
import lk.softvil.assignment.eventm.exception.UserNotFoundException;
import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.repository.UserRepository;
import lk.softvil.assignment.eventm.security.jwt.JwtService;
import lk.softvil.assignment.eventm.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final OtpService otpService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    public Map<String, String> initiateLogin(String email) {

        if (userRepository.findByEmail(email).isEmpty()) {
            System.out.println("User Not Found");
            throw new UserNotFoundException("User not found");
        }

        String otp = otpService.generateOtp(email);
        emailService.sendOtpEmail(email, otp);

        return Map.of("message", "OTP sent to your email -"+otp+" ");
    }

    public Map<String, String> verifyOtp(String email, String otp) {

        if (!otpService.validateOtp(email,otp)) {
            System.out.println("Invalid or expired OTP");
            throw new InvalidOtpException("Invalid or expired OTP");
        }

        // Get user from database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));



        // Generate JWT token
        String token = jwtService.generateToken(user);

        // Clear used OTP
        otpService.clearOtp(email);

        return Map.of(
                "token", token,
                "message", "Login successful",
                "userId", user.getId().toString(),
                "email", user.getEmail(),
                "role", user.getRole().name()
        );
    }
}