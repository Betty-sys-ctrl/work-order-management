package com.example.demo.exception;
import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;
@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}