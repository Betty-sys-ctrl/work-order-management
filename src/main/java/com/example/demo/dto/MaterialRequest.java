package com.example.demo.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
@Data
public class MaterialRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "SKU is mandatory")
    private String sku;
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;
}