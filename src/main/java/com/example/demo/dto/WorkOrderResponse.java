package com.example.demo.dto;
import com.example.demo.model.Status;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class WorkOrderResponse {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Long technicianId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderMaterialResponse> materials;
}