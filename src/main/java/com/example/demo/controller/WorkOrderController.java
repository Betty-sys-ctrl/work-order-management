package com.example.demo.controller;
import com.example.demo.dto.WorkOrderRequest;
import com.example.demo.dto.WorkOrderResponse;
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
}