package com.example.demo.dto;
import lombok.Data;
@Data
public class TechnicianRequest {
    private String name;
    private String email;
    private String specialty;
    private Boolean active;
}