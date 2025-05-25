package lk.softvil.assignment.eventm.security.controller;

import lk.softvil.assignment.eventm.dto.VerifyOtpRequest;
import lk.softvil.assignment.eventm.security.service.AuthService;
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

            return ResponseEntity.ok(authService.initiateLogin(email));

    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestBody VerifyOtpRequest verifyOtpRequest) {

        return ResponseEntity.ok().body(
              authService.verifyOtp(verifyOtpRequest.email(), verifyOtpRequest.otp())
        );
    }
}