package lk.softvil.assignment.eventm.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(String message, String error, int status, LocalDateTime timestamp, String path
) {
    // Basic constructor for simple errors
    public ErrorResponse(String message) {
        this(message, "Not Found", // default error type
                HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), null);
    }

    // Full constructor for detailed responses
    public ErrorResponse(String message, String error, HttpStatus status, String path) {
        this(message, error, status.value(), LocalDateTime.now(), path);
    }
}