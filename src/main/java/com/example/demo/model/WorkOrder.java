package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;
    
    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL)
    private List<StatusHistory> statusHistories;
    
    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL)
    private List<OrderMaterial> orderMaterials;
}