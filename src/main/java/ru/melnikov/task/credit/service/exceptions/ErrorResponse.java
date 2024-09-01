package ru.melnikov.task.credit.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponse(String error, String message, HttpStatus status) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = status.value();
        this.error = error;
        this.message = message;
    }
}

