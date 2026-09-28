package com.example.demo.config;

import com.example.demo.model.Material;
import com.example.demo.model.SystemUser;
import com.example.demo.model.Technician;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.SystemUserRepository;
import com.example.demo.repository.TechnicianRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final TechnicianRepository technicianRepository;
    private final MaterialRepository materialRepository;
    private final SystemUserRepository systemUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker;

    public DatabaseSeeder(TechnicianRepository technicianRepository, MaterialRepository materialRepository, 
                          SystemUserRepository systemUserRepository, PasswordEncoder passwordEncoder) {
        this.technicianRepository = technicianRepository;
        this.materialRepository = materialRepository;
        this.systemUserRepository = systemUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {
        if (systemUserRepository.count() == 0) {
            SystemUser admin = new SystemUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            systemUserRepository.save(admin);
            log.info("Data Seeding: Usuario administrador por defecto creado.");
        } else {
            log.info("La tabla de Usuarios ya estÃƒÂ¡ poblada. Omitiendo siembra.");
        }

        if (technicianRepository.count() == 0) {
            List<Technician> technicians = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String uniqueHash = UUID.randomUUID().toString().substring(0,4);
                technicians.add(new Technician(null, firstName + " " + lastName, firstName.toLowerCase() + "." + lastName.toLowerCase() + uniqueHash + "@example.com", faker.job().position(), true, null));
            }
            technicianRepository.saveAll(technicians);
            log.info("Data Seeding: 50 TÃƒÂ©cnicos insertados.");
        } else {
            log.info("La tabla de TÃƒÂ©cnicos ya estÃƒÂ¡ poblada. Omitiendo siembra.");
        }

        if (materialRepository.count() == 0) {
            List<Material> materials = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                String uniqueHash = UUID.randomUUID().toString().substring(0,4).toUpperCase();
                materials.add(new Material(null, faker.commerce().productName(), faker.regexify("[A-Z0-9]{4}") + uniqueHash, faker.number().numberBetween(10, 501), null));
            }
            materialRepository.saveAll(materials);
            log.info("Data Seeding: 50 Materiales insertados.");
        } else {
            log.info("La tabla de Materiales ya estÃƒÂ¡ poblada. Omitiendo siembra.");
        }
    }
}