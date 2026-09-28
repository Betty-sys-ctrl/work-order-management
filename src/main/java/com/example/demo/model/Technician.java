package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Technician {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    private String specialty;
    private Boolean active;
    
    @OneToMany(mappedBy = "technician")
    private List<WorkOrder> workOrders;

    public Technician() {
    }

    public Technician(Long id, String name, String email, String specialty, Boolean active, List<WorkOrder> workOrders) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.specialty = specialty;
        this.active = active;
        this.workOrders = workOrders;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }
}