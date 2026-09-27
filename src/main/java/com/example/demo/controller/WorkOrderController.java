package com.example.demo.controller;
import com.example.demo.dto.WorkOrderRequest;
import com.example.demo.dto.WorkOrderResponse;
import com.example.demo.dto.ChangeStatusRequest;
import com.example.demo.service.WorkOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/orders")
public class WorkOrderController {
    private final WorkOrderService service;
    public WorkOrderController(WorkOrderService service) { this.service = service; }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkOrderResponse createOrder(@RequestBody WorkOrderRequest request) {
        return service.createOrder(request);
    }
    
    @PutMapping("/{id}/assign/{technicianId}")
    public WorkOrderResponse assignTechnician(@PathVariable Long id, @PathVariable Long technicianId) {
        return service.assignTechnician(id, technicianId);
    }
    
    @PutMapping("/{id}/status")
    public WorkOrderResponse changeStatus(@PathVariable Long id, @RequestBody ChangeStatusRequest request) {
        return service.changeStatus(id, request);
    }
}