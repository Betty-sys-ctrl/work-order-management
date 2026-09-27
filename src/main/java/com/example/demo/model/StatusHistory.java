package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StatusHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status previousStatus;
    @Enumerated(EnumType.STRING)
    private Status newStatus;
    private LocalDateTime changedAt;
    
    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;
}