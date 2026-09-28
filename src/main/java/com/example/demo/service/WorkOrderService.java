package com.example.demo.service;
import com.example.demo.dto.WorkOrderRequest;
import com.example.demo.dto.WorkOrderResponse;
import com.example.demo.dto.ChangeStatusRequest;
import com.example.demo.dto.OrderMaterialRequest;
import com.example.demo.dto.OrderMaterialResponse;
import com.example.demo.model.Status;
import com.example.demo.model.StatusHistory;
import com.example.demo.model.Technician;
import com.example.demo.model.WorkOrder;
import com.example.demo.model.Material;
import com.example.demo.model.OrderMaterial;
import com.example.demo.repository.StatusHistoryRepository;
import com.example.demo.repository.TechnicianRepository;
import com.example.demo.repository.WorkOrderRepository;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.OrderMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
@Service
public class WorkOrderService {
    private final WorkOrderRepository orderRepository;
    private final TechnicianRepository technicianRepository;
    private final StatusHistoryRepository statusHistoryRepository;
    private final MaterialRepository materialRepository;
    private final OrderMaterialRepository orderMaterialRepository;
    
    public WorkOrderService(WorkOrderRepository orderRepository, TechnicianRepository technicianRepository, 
                            StatusHistoryRepository statusHistoryRepository, MaterialRepository materialRepository, 
                            OrderMaterialRepository orderMaterialRepository) {
        this.orderRepository = orderRepository;
        this.technicianRepository = technicianRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.materialRepository = materialRepository;
        this.orderMaterialRepository = orderMaterialRepository;
    }
    
    public org.springframework.data.domain.Page<WorkOrderResponse> searchOrders(Status status, Long technicianId, org.springframework.data.domain.Pageable pageable) {
        return orderRepository.findByFilters(status, technicianId, pageable).map(this::mapToResponse);
    }

    public WorkOrderResponse getOrderById(Long id) {
        WorkOrder order = orderRepository.findById(id).orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Order not found"));
        return mapToResponse(order);
    }

    public java.util.List<com.example.demo.dto.StatusHistoryResponse> getHistory(Long orderId) {
        return statusHistoryRepository.findByWorkOrderIdOrderByChangedAtDesc(orderId).stream().map(h -> {
            com.example.demo.dto.StatusHistoryResponse res = new com.example.demo.dto.StatusHistoryResponse();
            res.setId(h.getId());
            res.setPreviousStatus(h.getPreviousStatus());
            res.setNewStatus(h.getNewStatus());
            res.setChangedAt(h.getChangedAt());
            return res;
        }).collect(java.util.stream.Collectors.toList());
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
    
    @Transactional
    public WorkOrderResponse assignTechnician(Long orderId, Long technicianId) {
        WorkOrder order = orderRepository.findById(orderId)
            .orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Order not found"));
        Technician tech = technicianRepository.findById(technicianId)
            .orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Technician not found"));
        order.setTechnician(tech);
        order.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(orderRepository.save(order));
    }

    @Transactional
    public WorkOrderResponse changeStatus(Long orderId, ChangeStatusRequest request) {
        WorkOrder order = orderRepository.findById(orderId)
            .orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Order not found"));
            
        Status previousStatus = order.getStatus();
        order.setStatus(request.getNewStatus());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        
        StatusHistory history = StatusHistory.builder()
                .workOrder(order)
                .previousStatus(previousStatus)
                .newStatus(request.getNewStatus())
                .changedAt(LocalDateTime.now())
                .build();
        statusHistoryRepository.save(history);
        
        return mapToResponse(order);
    }
    
    @Transactional
    public OrderMaterialResponse useMaterial(Long orderId, OrderMaterialRequest request) {
        WorkOrder order = orderRepository.findById(orderId)
            .orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Order not found"));
            
        Material material = materialRepository.findById(request.getMaterialId())
            .orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Material not found"));
            
        if (material.getStockQuantity() == null || material.getStockQuantity() < request.getQuantityUsed()) {
            throw new com.example.demo.exception.BusinessRuleException("Insufficient stock for material ID: " + material.getId());
        }
        
        material.setStockQuantity(material.getStockQuantity() - request.getQuantityUsed());
        materialRepository.save(material);
        
        OrderMaterial orderMaterial = OrderMaterial.builder()
                .workOrder(order)
                .material(material)
                .quantityUsed(request.getQuantityUsed())
                .build();
        orderMaterialRepository.save(orderMaterial);
        
        OrderMaterialResponse res = new OrderMaterialResponse();
        res.setId(orderMaterial.getId());
        res.setWorkOrderId(order.getId());
        res.setMaterialId(material.getId());
        res.setQuantityUsed(orderMaterial.getQuantityUsed());
        return res;
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