package com.example.demo.dto;
import com.example.demo.model.Status;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class StatusHistoryResponse {
    private Long id;
    private Status previousStatus;
    private Status newStatus;
    private LocalDateTime changedAt;
}