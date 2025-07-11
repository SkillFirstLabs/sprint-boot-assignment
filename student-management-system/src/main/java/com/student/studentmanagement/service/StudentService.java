package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Student;
import org.springframework.data.domain.*;

import java.util.Optional;

public interface StudentService {
    Student createStudent(Student student);
    Optional<Student> getStudentById(Long id);
    Page<Student> getAllStudents(String status, Double minGpa, Double maxGpa, String name, Pageable pageable);
    Student updateStudent(Long id, Student updatedStudent);
    void deleteStudent(Long id);
}
