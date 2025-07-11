package com.srinath.student_management.service;

import com.srinath.student_management.model.Student;
import com.srinath.student_management.model.Student.Status;
import com.srinath.student_management.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student createStudent(@Valid Student student) {
        student.setStatus(Status.ACTIVE); // default status
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));
    }

    public Student updateStudent(Long id, @Valid Student updatedStudent) {
        Student student = getStudentById(id);
        updatedStudent.setId(id);
        return studentRepository.save(updatedStudent);
    }

    public void softDeleteStudent(Long id) {
        Student student = getStudentById(id);
        student.setStatus(Status.INACTIVE);
        studentRepository.save(student);
    }

    public Page<Student> getAllStudents(
            int page, int size, List<Sort.Order> sortOrders,
            String status, Double minGpa, Double maxGpa, String search
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrders));

        List<Student> filtered = studentRepository.findAll().stream()
            .filter(s -> {
                boolean match = true;
                if (status != null) {
                    match &= s.getStatus().toString().equalsIgnoreCase(status);
                }
                if (minGpa != null) {
                    match &= s.getGpa() >= minGpa;
                }
                if (maxGpa != null) {
                    match &= s.getGpa() <= maxGpa;
                }
                if (search != null && !search.isEmpty()) {
                    String fullName = (s.getFirstName() + " " + s.getLastName()).toLowerCase();
                    match &= fullName.contains(search.toLowerCase());
                }
                return match;
            }).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        List<Student> pageContent = (start < end) ? filtered.subList(start, end) : List.of();

        return new PageImpl<>(pageContent, pageable, filtered.size());
    }
}
