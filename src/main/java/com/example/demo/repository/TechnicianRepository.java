package com.example.demo.repository;
import com.example.demo.model.Technician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    long countByActiveTrue();
    List<Technician> findByActiveTrue();
    Page<Technician> findAll(Pageable pageable);
}