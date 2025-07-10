package com.example.student.controller;

import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.spec.StudentSpecifications;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepo;

    public StudentController(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentRepo.save(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return studentRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student updatedStudent) {
        return studentRepo.findById(id)
                .map(existing -> {
                    updatedStudent.setId(existing.getId());
                    return ResponseEntity.ok(studentRepo.save(updatedStudent));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        student.setStatus(Student.Status.INACTIVE);
        studentRepo.save(student);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Student>> listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(required = false) Student.Status status,
            @RequestParam(required = false) Double minGpa,
            @RequestParam(required = false) Double maxGpa,
            @RequestParam(required = false) String name
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));

        Specification<Student> spec = Specification.where(null);
        if (status != null) spec = spec.and(StudentSpecifications.hasStatus(status));
        if (minGpa != null && maxGpa != null) spec = spec.and(StudentSpecifications.gpaBetween(minGpa, maxGpa));
        if (name != null) spec = spec.and(StudentSpecifications.nameContains(name));

        return ResponseEntity.ok(studentRepo.findAll(spec, pageable));
    }

    private Sort.Order parseSort(String sortParam) {
        String[] parts = sortParam.split(",");
        String property = parts[0];
        String direction = (parts.length > 1) ? parts[1] : "asc";
        return new Sort.Order(Sort.Direction.fromString(direction.toUpperCase()), property);
    }
}
