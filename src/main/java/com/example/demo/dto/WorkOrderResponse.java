package com.example.demo.dto;

import com.example.demo.model.Priority;
import com.example.demo.model.WorkOrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkOrderResponse {
    private Long id;
    private String title;
    private String description;
    private WorkOrderStatus status;
    private Priority priority;
    private String customerName;
    private String assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
