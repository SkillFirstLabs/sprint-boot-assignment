package com.example.StudentManagementSystem.Controller;

import com.example.StudentManagementSystem.Repository.StudentRepository;
import com.example.StudentManagementSystem.enums.Status;
import jakarta.validation.Valid;
import com.example.StudentManagementSystem.Services.StudentService;
import com.example.StudentManagementSystem.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    StudentService studentservice;
    @Autowired
    private StudentService studentService;
    @Autowired
    private HandlerMapping resourceHandlerMapping;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student saved = studentservice.createStudent(student);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    /*@PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        Student existing = studentService.getStudentById(id);
        if (student.getFirstname() != null) {
            existing.setFirstname(student.getFirstname());
        }
        if (student.getLastname() != null) {
            existing.setLastname(student.getLastname());
        }
        if (student.getEmail() != null) {
            existing.setEmail(student.getEmail());
        }
        if (student.getDateOfBirth() != null) {
            existing.setDateOfBirth(student.getDateOfBirth());
        }
        if (student.getStatus() != null) {
            existing.setStatus(student.getStatus());
        }
        if (student.getEnrollmentDate() != null) {
            existing.setEnrollmentDate(student.getEnrollmentDate());
        }
        if (student.getGpa() != null) {
            existing.setGpa(student.getGpa());
        }
        Student updated = studentService.save(existing);
        return ResponseEntity.ok(updated);
    }*/
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        updates.forEach((key, value) -> {
            try {
                switch (key) {
                    case "firstName" -> student.setFirstname((String) value);
                    case "lastName" -> student.setLastname((String) value);
                    case "email" -> student.setEmail((String) value);
                    case "dateOfBirth" ->
                            student.setDateOfBirth(LocalDate.parse((String) value, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    case "enrollmentDate" ->
                            student.setEnrollmentDate(LocalDate.parse((String) value, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    case "gpa" -> student.setGpa(Double.valueOf(value.toString()));
                    case "status" ->
                            student.setStatus(Status.valueOf(((String) value).toUpperCase()));

                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid value for field: " + key + " - " + e.getMessage());
            }
        });

        Student updated = studentRepository.save(student);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){

    try{
      Student student =studentservice.softDeleteStudent(id);
      return ResponseEntity.ok("student with "+ id+" is set INACTIVE");
    }
    catch(RuntimeException e){
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    }
    @GetMapping("/filter/status")
    public ResponseEntity<?>getStudentsByStatus(@RequestParam Status status){
        List<Student> students= studentService.getStudentsByStatus(status);
        if(students.isEmpty()){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }
    @GetMapping("/filter/gpa")
    public ResponseEntity<?>getStudentsByGpaBetween(@RequestParam Double minGpa,@RequestParam Double maxGpa){
        List<Student> students= studentService.getStudentsByGpaBetween(minGpa,maxGpa);
        if(students.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }
    @GetMapping("/filter/Name")
    public ResponseEntity<?>getStudentsByName(@RequestParam String name){
        List<Student> students = studentService.searchStudentsByName(name);
        if(students.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }
    @GetMapping
    public ResponseEntity<Map<String,Object>>getStudents(@RequestParam(required=false)Status status,
                                                         @RequestParam(required=false)Double minGpa,
                                                         @RequestParam(required=false)Double maxGpa,
                                                         @RequestParam(required=false)String keyword,
                                                         @RequestParam(defaultValue = "0")int page,
                                                         @RequestParam(defaultValue="10")int size,
                                                         @RequestParam(defaultValue ="id,asc")String[]sort
                                                         ){
        List<Sort.Order> orders = new ArrayList<>();
        for(String OrderParameter:sort){
           String[] parts=OrderParameter.split(",");
           if(parts.length==2){
               orders.add(new Sort.Order(Sort.Direction.fromString(parts[1]), parts[0]));
           }
        }
        Page<Student> result = studentService.getStudents(status, minGpa, maxGpa, keyword, page, size, orders);
        Map<String, Object> response = new HashMap<>();
        response.put("students", result.getContent());
        response.put("currentPage", result.getNumber());
        response.put("totalItems", result.getTotalElements());
        response.put("totalPages", result.getTotalPages());
        return ResponseEntity.ok(response);

    }






 }
