package lk.softvil.assignment.eventm.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidOtpException extends RuntimeException {

    public InvalidOtpException(String message) {
        super(message);
    }

    public InvalidOtpException(String email, String otpType) {
        super(String.format("Invalid or expired %s OTP for email: %s", otpType, email));
    }

    public InvalidOtpException() {
        super("Invalid OTP");
    }
}