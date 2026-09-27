package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Material {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String sku;
    private Integer stockQuantity;
    @OneToMany(mappedBy = "material")
    private List<OrderMaterial> orderMaterials;
}