package com.example.demo.config;

import com.example.demo.model.Material;
import com.example.demo.model.Technician;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.TechnicianRepository;
import com.example.demo.model.SystemUser;
import com.example.demo.repository.SystemUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final TechnicianRepository technicianRepository;
    private final MaterialRepository materialRepository;
    private final SystemUserRepository systemUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker;

    public DatabaseSeeder(TechnicianRepository technicianRepository, MaterialRepository materialRepository, SystemUserRepository systemUserRepository, PasswordEncoder passwordEncoder) {
        this.technicianRepository = technicianRepository;
        this.materialRepository = materialRepository;
        this.systemUserRepository = systemUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {
        seedTechnicians();
        seedMaterials();
        seedAdmin();
    }

    private void seedAdmin() {
        if (systemUserRepository.count() == 0) {
            SystemUser admin = SystemUser.builder().username("admin").password(passwordEncoder.encode("admin123")).build();
            systemUserRepository.save(admin);
            System.out.println("✅ Data Seeding: Default admin user created.");
        }
    }

    private void seedTechnicians() {
        if (technicianRepository.count() == 0) {
            List<Technician> technicians = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                technicians.add(generateTechnician());
            }
            technicianRepository.saveAll(technicians);
            System.out.println("âœ… Data Seeding: 50 Technicians created.");
        } else {
            List<Technician> existing = technicianRepository.findAll();
            boolean updated = false;
            for (Technician tech : existing) {
                if (!isValidTechnician(tech)) {
                    Technician generated = generateTechnician();
                    tech.setName(generated.getName());
                    tech.setEmail(generated.getEmail());
                    tech.setSpecialty(generated.getSpecialty());
                    tech.setActive(generated.getActive());
                    updated = true;
                }
            }
            if (updated) {
                technicianRepository.saveAll(existing);
                System.out.println("âœ… Data Seeding: Invalid Technicians cleaned and updated.");
            }
        }
    }

    private void seedMaterials() {
        if (materialRepository.count() == 0) {
            List<Material> materials = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                materials.add(generateMaterial());
            }
            materialRepository.saveAll(materials);
            System.out.println("âœ… Data Seeding: 50 Materials created.");
        } else {
            List<Material> existing = materialRepository.findAll();
            boolean updated = false;
            for (Material mat : existing) {
                if (!isValidMaterial(mat)) {
                    Material generated = generateMaterial();
                    mat.setName(generated.getName());
                    mat.setSku(generated.getSku());
                    mat.setStockQuantity(generated.getStockQuantity());
                    updated = true;
                }
            }
            if (updated) {
                materialRepository.saveAll(existing);
                System.out.println("âœ… Data Seeding: Invalid Materials cleaned and updated.");
            }
        }
    }

    private Technician generateTechnician() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return Technician.builder()
                .name(firstName + " " + lastName)
                .email(firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com")
                .specialty(faker.job().position())
                .active(true)
                .build();
    }

    private Material generateMaterial() {
        return Material.builder()
                .name(faker.commerce().productName())
                .sku(faker.regexify("[A-Z0-9]{8}"))
                .stockQuantity(faker.number().numberBetween(10, 501))
                .build();
    }

    private boolean isValidTechnician(Technician tech) {
        if (tech.getName() == null || !tech.getName().contains(" ")) return false;
        if (tech.getEmail() == null || !tech.getEmail().contains("@")) return false;
        if (tech.getSpecialty() == null || tech.getSpecialty().trim().isEmpty()) return false;
        return true;
    }

    private boolean isValidMaterial(Material mat) {
        if (mat.getName() == null || mat.getName().trim().isEmpty()) return false;
        if (mat.getSku() == null || !Pattern.matches("^[A-Z0-9]+$", mat.getSku())) return false;
        if (mat.getStockQuantity() == null || mat.getStockQuantity() < 0) return false;
        return true;
    }
}