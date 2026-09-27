package com.example.demo.dto;
import lombok.Data;
@Data
public class OrderMaterialRequest {
    private Long materialId;
    private Integer quantityUsed;
}