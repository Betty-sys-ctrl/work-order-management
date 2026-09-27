package com.example.demo.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
@Data
public class WorkOrderRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Description is mandatory")
    private String description;
    private Long technicianId;
}