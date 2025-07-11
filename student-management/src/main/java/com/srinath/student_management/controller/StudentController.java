package com.srinath.student_management.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srinath.student_management.model.Student;
import com.srinath.student_management.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // Create a student
    @PostMapping
    public Student createStudent(@RequestBody @Valid Student student) {
        return studentService.createStudent(student);
    }

    // Get student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // Update student by ID
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody @Valid Student student) {
        return studentService.updateStudent(id, student);
    }

    // Soft delete student by ID
    @DeleteMapping("/{id}")
    public void softDeleteStudent(@PathVariable Long id) {
        studentService.softDeleteStudent(id);
    }

    // Get all students with pagination, filtering, sorting, and search
    @GetMapping
    public Page<Student> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") List<String> sort,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Double minGpa,
            @RequestParam(required = false) Double maxGpa,
            @RequestParam(required = false) String search
    ) {
        List<Sort.Order> sortOrders = sort.stream().map(s -> {
            String[] parts = s.split(",");
            String property = parts[0];
            String direction = (parts.length > 1) ? parts[1] : "asc";
            return new Sort.Order(Sort.Direction.fromString(direction), property);
        }).toList();

        return studentService.getAllStudents(page, size, sortOrders, status, minGpa, maxGpa, search);
    }
}
