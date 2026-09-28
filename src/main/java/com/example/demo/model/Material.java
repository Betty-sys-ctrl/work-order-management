package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Material {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String sku;
    private Integer stockQuantity;
    
    @OneToMany(mappedBy = "material")
    private List<OrderMaterial> orderMaterials;

    public Material() {
    }

    public Material(Long id, String name, String sku, Integer stockQuantity, List<OrderMaterial> orderMaterials) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.stockQuantity = stockQuantity;
        this.orderMaterials = orderMaterials;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<OrderMaterial> getOrderMaterials() {
        return orderMaterials;
    }

    public void setOrderMaterials(List<OrderMaterial> orderMaterials) {
        this.orderMaterials = orderMaterials;
    }
}