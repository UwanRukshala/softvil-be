package lk.softvil.assignment.eventm.security.controller;

import lk.softvil.assignment.eventm.repository.UserRepository;
import lk.softvil.assignment.eventm.security.service.AuthService;
import lk.softvil.assignment.eventm.security.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<?> testRoute(){
        return ResponseEntity.status(200).body("Hello");
    }

    @PostMapping("/initiate-login")
    public ResponseEntity<?> initiateLogin(@RequestParam String email) {
        try {
            return ResponseEntity.ok(authService.initiateLogin(email));
        } catch (Exception e) {
            e.printStackTrace();  // Or log.error(...)
            return ResponseEntity.status(500).body("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {



        return ResponseEntity.ok().body(
              authService.verifyOtp(email, otp)
        );
    }
}