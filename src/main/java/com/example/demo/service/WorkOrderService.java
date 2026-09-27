package com.example.demo.service;
import com.example.demo.dto.WorkOrderRequest;
import com.example.demo.dto.WorkOrderResponse;
import com.example.demo.model.Status;
import com.example.demo.model.WorkOrder;
import com.example.demo.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
@Service
public class WorkOrderService {
    private final WorkOrderRepository orderRepository;
    
    public WorkOrderService(WorkOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Transactional
    public WorkOrderResponse createOrder(WorkOrderRequest request) {
        WorkOrder order = WorkOrder.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return mapToResponse(orderRepository.save(order));
    }
    
    private WorkOrderResponse mapToResponse(WorkOrder order) {
        WorkOrderResponse res = new WorkOrderResponse();
        res.setId(order.getId());
        res.setTitle(order.getTitle());
        res.setDescription(order.getDescription());
        res.setStatus(order.getStatus());
        res.setCreatedAt(order.getCreatedAt());
        res.setUpdatedAt(order.getUpdatedAt());
        if (order.getTechnician() != null) {
            res.setTechnicianId(order.getTechnician().getId());
        }
        return res;
    }
}