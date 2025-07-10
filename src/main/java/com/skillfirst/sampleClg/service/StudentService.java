package com.skillfirst.sampleClg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillfirst.sampleClg.model.Student;
import com.skillfirst.sampleClg.repository.StudentRepo;

@Service
public class StudentService {

    @Autowired
    private StudentRepo repo;

    // Save a single student
    public ResponseEntity<String> saveStudDetails(Student stud) {
        try {
            repo.save(stud);
            return ResponseEntity.ok("Student details saved successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Failed to save student details: " + e.getMessage());
        }
    }

    // Get a single student by ID
    public Optional<Student> getStudent(int id) {
        return repo.findById(id);
    }

    // Update student details
    public ResponseEntity<String> updateStudent(int id, Student updatedStudent) {
        Optional<Student> optionalStud = repo.findById(id);
        if (optionalStud.isPresent()) {
            Student stud = optionalStud.get();
            stud.setFirstName(updatedStudent.getFirstName());
            stud.setLastName(updatedStudent.getLastName());
            stud.setEmail(updatedStudent.getEmail());
            stud.setDateOfBirth(updatedStudent.getDateOfBirth());
            stud.setStatus(updatedStudent.getStatus());
            stud.setEnrollmentDate(updatedStudent.getEnrollmentDate());
            stud.setGpa(updatedStudent.getGpa());

            try {
                repo.save(stud);
                return ResponseEntity.ok("Student updated successfully");
            } catch (Exception e) {
                return ResponseEntity
                        .status(500)
                        .body("Failed to update student details: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(404).body("Student not found with ID: " + id);
        }
    }

    // Soft delete student by marking status as INACTIVE
    public ResponseEntity<String> softDeleteStudent(int id) {
        Optional<Student> optionalStud = repo.findById(id);
        if (optionalStud.isPresent()) {
            Student stud = optionalStud.get();
            stud.setStatus("INACTIVE");
            repo.save(stud);
            return ResponseEntity.ok("Student marked as INACTIVE");
        } else {
            return ResponseEntity.status(404).body("Student not found with ID: " + id);
        }
    }

    // Get students with pagination, sorting, and optional filtering
    public Page<Student> getStudentsWithPageAndFilter(
            int page,
            int size,
            String sortField,
            String sortDirection,
            String status,
            Double minGpa) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (status != null && minGpa != null) {
            return repo.findByStatusAndGpaGreaterThanEqual(status, minGpa, pageable);
        } else if (status != null) {
            return repo.findByStatus(status, pageable);
        } else if (minGpa != null) {
            return repo.findByGpaGreaterThanEqual(minGpa, pageable);
        } else {
            return repo.findAll(pageable);
        }
    }
}
