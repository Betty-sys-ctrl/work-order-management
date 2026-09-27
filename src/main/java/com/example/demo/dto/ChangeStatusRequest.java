package com.example.demo.dto;
import com.example.demo.model.Status;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
@Data
public class ChangeStatusRequest {
    @NotNull(message = "New status is mandatory")
    private Status newStatus;
}