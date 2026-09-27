package com.example.demo.dto;
import lombok.Data;
@Data
public class OrderMaterialResponse {
    private Long id;
    private Long workOrderId;
    private Long materialId;
    private Integer quantityUsed;
}