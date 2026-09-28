package com.example.demo.controller;
import com.example.demo.dto.WorkOrderRequest;
import com.example.demo.dto.WorkOrderResponse;
import com.example.demo.dto.ChangeStatusRequest;
import com.example.demo.dto.OrderMaterialRequest;
import com.example.demo.dto.OrderMaterialResponse;
import com.example.demo.service.WorkOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/orders")
public class WorkOrderController {
    private final WorkOrderService service;
    public WorkOrderController(WorkOrderService service) { this.service = service; }
    
    @GetMapping("/{id}/history")
    public java.util.List<com.example.demo.dto.StatusHistoryResponse> getHistory(@PathVariable Long id) {
        return service.getHistory(id);
    }
    
    @GetMapping("/{id}")
    public WorkOrderResponse getOrderById(@PathVariable Long id) {
        return service.getOrderById(id);
    }
    
    @GetMapping
    public org.springframework.data.domain.Page<WorkOrderResponse> searchOrders(
            @RequestParam(required = false) com.example.demo.model.Status status,
            @RequestParam(required = false) Long technicianId,
            org.springframework.data.domain.Pageable pageable) {
        return service.searchOrders(status, technicianId, pageable);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkOrderResponse createOrder(@Valid @RequestBody WorkOrderRequest request) {
        return service.createOrder(request);
    }
    
    @PutMapping("/{id}/assign/{technicianId}")
    public WorkOrderResponse assignTechnician(@PathVariable Long id, @PathVariable Long technicianId) {
        return service.assignTechnician(id, technicianId);
    }
    
    @PutMapping("/{id}/status")
    public WorkOrderResponse changeStatus(@PathVariable Long id, @Valid @RequestBody ChangeStatusRequest request) {
        return service.changeStatus(id, request);
    }
    
    @PostMapping("/{id}/materials")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderMaterialResponse useMaterial(@PathVariable Long id, @Valid @RequestBody OrderMaterialRequest request) {
        return service.useMaterial(id, request);
    }
}