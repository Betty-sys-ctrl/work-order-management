package com.example.demo.config;

import com.example.demo.model.Material;
import com.example.demo.model.Technician;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.TechnicianRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner initData(TechnicianRepository technicianRepository, MaterialRepository materialRepository) {
        return args -> {
            if (technicianRepository.count() == 0) {
                technicianRepository.saveAll(List.of(
                    Technician.builder()
                        .name("John Doe")
                        .email("john@example.com")
                        .specialty("Electronics")
                        .active(true)
                        .build(),
                    Technician.builder()
                        .name("Jane Smith")
                        .email("jane@example.com")
                        .specialty("Plumbing")
                        .active(false)
                        .build()
                ));
                System.out.println("✅ Data Seeding: Technicians created.");
            }

            if (materialRepository.count() == 0) {
                materialRepository.saveAll(List.of(
                    Material.builder()
                        .name("Copper Wire")
                        .sku("CW-001")
                        .stockQuantity(100)
                        .build(),
                    Material.builder()
                        .name("PVC Pipe")
                        .sku("PVC-002")
                        .stockQuantity(50)
                        .build(),
                    Material.builder()
                        .name("Circuit Breaker")
                        .sku("CB-003")
                        .stockQuantity(20)
                        .build()
                ));
                System.out.println("✅ Data Seeding: Materials created.");
            }
        };
    }
}