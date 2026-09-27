package com.example.demo.dto;
import lombok.Data;
@Data
public class WorkOrderRequest {
    private String title;
    private String description;
    private Long technicianId;
}