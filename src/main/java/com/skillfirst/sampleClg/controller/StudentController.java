package com.skillfirst.sampleClg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillfirst.sampleClg.model.Student;
import com.skillfirst.sampleClg.service.StudentService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService serv;

    @PostMapping("/students")
    public ResponseEntity<?> createStudents(@Valid @RequestBody List<Student> students) {
        for (Student stud : students) {
            ResponseEntity<String> response = serv.saveStudDetails(stud);
            if (!response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity
                        .status(response.getStatusCode())
                        .body("Failed to save student: " + stud.getFirstName());
            }
        }
        return ResponseEntity.ok("All students saved successfully");
    }

    @GetMapping("/students")
    public List<Student> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Double minGpa) {

        String sortField = sort.length > 0 ? sort[0] : "id";
        String sortDirection = sort.length > 1 ? sort[1] : "asc";
        return serv.getStudentsWithPageAndFilter(page, size, sortField, sortDirection, status, minGpa).getContent();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable int id) {
        return serv.getStudent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(null));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable int id,
            @Valid @RequestBody Student updatedStudent) {
        return serv.updateStudent(id, updatedStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> softDeleteStudent(@PathVariable int id) {
        return serv.softDeleteStudent(id);
    }
}
