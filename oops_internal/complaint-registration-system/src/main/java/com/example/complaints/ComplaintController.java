package com.example.complaints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ComplaintController {

    @Autowired
    private ComplaintRepository repository;

    @GetMapping("/complaints")
    public List<Complaint> getAll() {
        return repository.findAll();
    }

    @GetMapping("/complaint/{id}")
    public Optional<Complaint> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping("/complaints/status/open")
    public List<Complaint> getOpenComplaints() {
        return repository.findByStatus("open");
    }

    @PostMapping("/complaint")
    public Complaint createComplaint(@RequestBody Complaint complaint) {
        return repository.save(complaint);
    }

    @PutMapping("/complaint/{id}")
    public Complaint updateComplaint(@PathVariable Long id, @RequestBody Complaint updated) {
        return repository.findById(id).map(c -> {
            c.setStudentName(updated.getStudentName());
            c.setComplaintType(updated.getComplaintType());
            c.setDescription(updated.getDescription());
            c.setStatus(updated.getStatus());
            return repository.save(c);
        }).orElseGet(() -> {
            updated.setId(id);
            return repository.save(updated);
        });
    }

    @DeleteMapping("/complaint/{id}")
    public void deleteComplaint(@PathVariable Long id) {
        repository.deleteById(id);
    }
}