package com.example.demo.controller;

import com.example.demo.dto.DashboardMetricsDTO;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.TechnicianRepository;
import com.example.demo.repository.WorkOrderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    private final WorkOrderRepository workOrderRepository;
    private final TechnicianRepository technicianRepository;
    private final MaterialRepository materialRepository;

    public MetricsController(WorkOrderRepository workOrderRepository, TechnicianRepository technicianRepository, MaterialRepository materialRepository) {
        this.workOrderRepository = workOrderRepository;
        this.technicianRepository = technicianRepository;
        this.materialRepository = materialRepository;
    }

    @GetMapping("/dashboard")
    public DashboardMetricsDTO getDashboardMetrics() {
        long activeOrders = workOrderRepository.countByStatusIn(List.of(com.example.demo.model.Status.PENDING, com.example.demo.model.Status.IN_PROGRESS));
        long activeTechs = technicianRepository.countByActiveTrue();
        
        List<DashboardMetricsDTO.MaterialStockDTO> lowStock = materialRepository.findByStockQuantityLessThan(20)
                .stream()
                .map(m -> new DashboardMetricsDTO.MaterialStockDTO(
                        m.getId(),
                        m.getName(),
                        m.getSku(),
                        m.getStockQuantity()
                ))
                .collect(Collectors.toList());

        return new DashboardMetricsDTO(
                activeOrders,
                activeTechs,
                lowStock
        );
    }
}