package lk.softvil.assignment.eventm.security.service;


import lk.softvil.assignment.eventm.repository.UserRepository;
import lk.softvil.assignment.eventm.service.EmailService;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthService {

    private final OtpService otpService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public AuthService(OtpService otpService,
                       EmailService emailService,
                       UserRepository userRepository) {
        this.otpService = otpService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public Map<String, String> initiateLogin(String email) {

        System.out.println(email);
        if (userRepository.findByEmail(email).isEmpty()) {
//            throw new UserNotFoundException("User not found");
            System.out.println("User Not Found");
        }

//        String otp = otpService.generateOtp(email);
//        emailService.sendOtpEmail(email, otp);

        return Map.of("message", "OTP sent to your email");
    }

    public Map<String, String> verifyOtp(String email, String otp) {

        if (!otpService.validateOtp(email,otp)) {
//            throw new InvalidOtpException("Invalid or expired OTP");
            System.out.println("Invalid or expired OTP");
        }

        return Map.of(
                "token", "generated-jwt-token",
                "message", "Login successful"
        );
    }
}