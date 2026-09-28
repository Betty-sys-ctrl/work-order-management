package com.example.demo.service;
import com.example.demo.dto.TechnicianRequest;
import com.example.demo.dto.TechnicianResponse;
import com.example.demo.model.Technician;
import com.example.demo.repository.TechnicianRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TechnicianService {
    private final TechnicianRepository repository;
    public TechnicianService(TechnicianRepository repository) { this.repository = repository; }
    
    public Page<TechnicianResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponse);
    }
    
    public TechnicianResponse getById(Long id) {
        Technician tech = repository.findById(id).orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Technician not found"));
        return mapToResponse(tech);
    }
    
    @Transactional
    public TechnicianResponse create(TechnicianRequest request) {
        Technician tech = Technician.builder()
                .name(request.getName())
                .email(request.getEmail())
                .specialty(request.getSpecialty())
                .active(request.getActive() != null ? request.getActive() : true)
                .build();
        return mapToResponse(repository.save(tech));
    }
    
    @Transactional
    public TechnicianResponse update(Long id, TechnicianRequest request) {
        Technician tech = repository.findById(id).orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Technician not found"));
        tech.setName(request.getName());
        tech.setEmail(request.getEmail());
        tech.setSpecialty(request.getSpecialty());
        if (request.getActive() != null) tech.setActive(request.getActive());
        return mapToResponse(repository.save(tech));
    }
    
    @Transactional
    public void delete(Long id) {
        Technician tech = repository.findById(id).orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Technician not found"));
        tech.setActive(false);
        repository.save(tech);
    }
    
    private TechnicianResponse mapToResponse(Technician tech) {
        TechnicianResponse res = new TechnicianResponse();
        res.setId(tech.getId());
        res.setName(tech.getName());
        res.setEmail(tech.getEmail());
        res.setSpecialty(tech.getSpecialty());
        res.setActive(tech.getActive());
        return res;
    }
}