package com.example.demo.dto;
import lombok.Data;
@Data
public class MaterialResponse {
    private Long id;
    private String name;
    private String sku;
    private Integer stockQuantity;
}