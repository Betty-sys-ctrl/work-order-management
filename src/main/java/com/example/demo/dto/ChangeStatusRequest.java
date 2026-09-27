package com.example.demo.dto;
import com.example.demo.model.Status;
import lombok.Data;
@Data
public class ChangeStatusRequest {
    private Status newStatus;
}