package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor public class Technician {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String specialty;
    private Boolean active;
    @OneToMany(mappedBy = "technician")
    private List<WorkOrder> workOrders;
}