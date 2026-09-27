package com.example.demo.controller;
import com.example.demo.dto.TechnicianRequest;
import com.example.demo.dto.TechnicianResponse;
import com.example.demo.service.TechnicianService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/technicians")
public class TechnicianController {
    private final TechnicianService service;
    public TechnicianController(TechnicianService service) { this.service = service; }
    
    @GetMapping
    public List<TechnicianResponse> getAll() { return service.getAll(); }
    
    @GetMapping("/{id}")
    public TechnicianResponse getById(@PathVariable Long id) { return service.getById(id); }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechnicianResponse create(@RequestBody TechnicianRequest request) { return service.create(request); }
    
    @PutMapping("/{id}")
    public TechnicianResponse update(@PathVariable Long id, @RequestBody TechnicianRequest request) {
        return service.update(id, request);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}