package com.example.demo.dto;
import lombok.Data;
@Data
public class MaterialRequest {
    private String name;
    private String sku;
    private Integer stockQuantity;
}