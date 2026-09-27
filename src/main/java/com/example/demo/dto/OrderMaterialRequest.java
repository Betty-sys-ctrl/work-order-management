package com.example.demo.dto;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
@Data
public class OrderMaterialRequest {
    @NotNull(message = "Material ID is mandatory")
    private Long materialId;
    @NotNull(message = "Quantity used is mandatory")
    @Min(value = 1, message = "Quantity used must be at least 1")
    private Integer quantityUsed;
}