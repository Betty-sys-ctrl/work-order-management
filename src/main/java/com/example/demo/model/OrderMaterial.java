package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor public class OrderMaterial {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantityUsed;
    
    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;
    
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
}