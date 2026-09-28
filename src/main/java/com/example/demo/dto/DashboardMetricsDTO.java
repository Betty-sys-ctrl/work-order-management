package com.example.demo.dto;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DashboardMetricsDTO {
    private long activeOrdersCount;
    private long activeTechniciansCount;
    private List<MaterialStockDTO> lowStockMaterials;

    @Data
    @Builder
    public static class MaterialStockDTO {
        private Long id;
        private String name;
        private String sku;
        private Integer stockQuantity;
    }
}