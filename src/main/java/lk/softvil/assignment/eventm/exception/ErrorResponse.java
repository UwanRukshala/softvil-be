package lk.softvil.assignment.eventm.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


public record ErrorResponse(
        String message,
        String error,
        long status,
        LocalDateTime timestamp,
        String path
) {
    // For simple not-found cases
    public ErrorResponse(String message) {
        this(message, "Not Found", HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), null);
    }

    // For detailed error responses
    public ErrorResponse(HttpStatus status, String message, String error,String path) {
        this(message, error, status.value(), LocalDateTime.now(), null);
    }

    // For errors without path context
    public ErrorResponse(HttpStatus status, String message, String error) {
        this(message, error, status.value(), LocalDateTime.now(), null);
    }
}