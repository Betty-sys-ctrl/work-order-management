package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class OrderMaterial {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer quantityUsed;
    
    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;
    
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    public OrderMaterial() {
    }

    public OrderMaterial(Long id, Integer quantityUsed, WorkOrder workOrder, Material material) {
        this.id = id;
        this.quantityUsed = quantityUsed;
        this.workOrder = workOrder;
        this.material = material;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Integer quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}