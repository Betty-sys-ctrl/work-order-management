package com.example.demo.controller;
import com.example.demo.dto.AddStockRequest;
import com.example.demo.dto.MaterialRequest;
import com.example.demo.dto.MaterialResponse;
import com.example.demo.service.MaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/materials")
public class MaterialController {
    private final MaterialService service;
    public MaterialController(MaterialService service) { this.service = service; }
    
    @GetMapping
    public List<MaterialResponse> getAll() { return service.getAll(); }
    
    @GetMapping("/{id}")
    public MaterialResponse getById(@PathVariable Long id) { return service.getById(id); }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialResponse create(@Valid @RequestBody MaterialRequest request) { return service.create(request); }
    
    @PutMapping("/{id}")
    public MaterialResponse update(@PathVariable Long id, @Valid @RequestBody MaterialRequest request) {
        return service.update(id, request);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
    
    @PatchMapping("/{id}/add-stock")
    public MaterialResponse addStock(@PathVariable Long id, @Valid @RequestBody AddStockRequest request) {
        return service.addStock(id, request.getQuantity());
    }
}