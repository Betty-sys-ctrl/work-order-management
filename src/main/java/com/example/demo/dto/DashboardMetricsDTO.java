package com.example.demo.dto;

import java.util.List;

public record DashboardMetricsDTO(
    long activeOrdersCount,
    long activeTechniciansCount,
    List<MaterialStockDTO> lowStockMaterials
) {
    public record MaterialStockDTO(
        Long id,
        String name,
        String sku,
        Integer stockQuantity
    ) {}
}