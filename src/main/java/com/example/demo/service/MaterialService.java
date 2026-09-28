package com.example.demo.service;
import com.example.demo.dto.MaterialRequest;
import com.example.demo.dto.MaterialResponse;
import com.example.demo.model.Material;
import com.example.demo.repository.MaterialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialService {
    private final MaterialRepository repository;
    public MaterialService(MaterialRepository repository) { this.repository = repository; }
    
    public Page<MaterialResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponse);
    }
    
    public MaterialResponse getById(Long id) {
        Material mat = repository.findById(id).orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Material not found"));
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
        Material mat = repository.findById(id).orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Material not found"));
        mat.setName(request.getName());
        mat.setSku(request.getSku());
        return mapToResponse(repository.save(mat));
    }
    
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    @Transactional
    public MaterialResponse addStock(Long id, Integer quantity) {
        Material mat = repository.findById(id).orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Material not found"));
        mat.setStockQuantity(mat.getStockQuantity() + quantity);
        return mapToResponse(repository.save(mat));
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