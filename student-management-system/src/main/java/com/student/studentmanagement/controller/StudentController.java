package com.student.studentmanagement.controller;

import com.student.studentmanagement.exception.StudentNotFoundException;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Student create(@Valid @RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .orElseThrow(() -> new StudentNotFoundException("Not found"));
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student s) {
        return studentService.updateStudent(id, s);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student soft deleted.";
    }

    @GetMapping
    public Page<Student> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Double minGpa,
            @RequestParam(required = false) Double maxGpa,
            @RequestParam(required = false) String name
    ) {
        Sort.Order order = new Sort.Order(
                Sort.Direction.fromString(sort[0].split(",")[1]),
                sort[0].split(",")[0]
        );
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        return studentService.getAllStudents(status, minGpa, maxGpa, name, pageable);
    }
}
