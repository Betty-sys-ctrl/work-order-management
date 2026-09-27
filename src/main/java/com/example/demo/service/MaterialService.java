package com.example.demo.service;
import com.example.demo.dto.MaterialRequest;
import com.example.demo.dto.MaterialResponse;
import com.example.demo.model.Material;
import com.example.demo.repository.MaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class MaterialService {
    private final MaterialRepository repository;
    public MaterialService(MaterialRepository repository) { this.repository = repository; }
    
    public List<MaterialResponse> getAll() {
        return repository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    
    public MaterialResponse getById(Long id) {
        Material mat = repository.findById(id).orElseThrow(() -> new RuntimeException("Material not found"));
        return mapToResponse(mat);
    }
    
    @Transactional
    public MaterialResponse create(MaterialRequest request) {
        Material mat = Material.builder()
                .name(request.getName())
                .sku(request.getSku())
                .stockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0)
                .build();
        return mapToResponse(repository.save(mat));
    }
    
    @Transactional
    public MaterialResponse update(Long id, MaterialRequest request) {
        Material mat = repository.findById(id).orElseThrow(() -> new RuntimeException("Material not found"));
        mat.setName(request.getName());
        mat.setSku(request.getSku());
        if (request.getStockQuantity() != null) mat.setStockQuantity(request.getStockQuantity());
        return mapToResponse(repository.save(mat));
    }
    
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    private MaterialResponse mapToResponse(Material mat) {
        MaterialResponse res = new MaterialResponse();
        res.setId(mat.getId());
        res.setName(mat.getName());
        res.setSku(mat.getSku());
        res.setStockQuantity(mat.getStockQuantity());
        return res;
    }
}