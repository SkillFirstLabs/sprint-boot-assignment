package com.student.studentmanagement.service;

import com.student.studentmanagement.exception.StudentNotFoundException;
import com.student.studentmanagement.model.*;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.specification.StudentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repo;

    @Override
    public Student createStudent(Student student) {
        return repo.save(student);
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Page<Student> getAllStudents(String status, Double minGpa, Double maxGpa, String name, Pageable pageable) {
        Specification<Student> spec = Specification
                .where(StudentSpecification.hasStatus(status))
                .and(StudentSpecification.hasMinGpa(minGpa))
                .and(StudentSpecification.hasMaxGpa(maxGpa))
                .and(StudentSpecification.nameContains(name));
        return repo.findAll(spec, pageable);
    }

    @Override
    public Student updateStudent(Long id, Student updated) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
        student.setFirstName(updated.getFirstName());
        student.setLastName(updated.getLastName());
        student.setEmail(updated.getEmail());
        student.setDateOfBirth(updated.getDateOfBirth());
        student.setEnrollmentDate(updated.getEnrollmentDate());
        student.setGpa(updated.getGpa());
        student.setStatus(updated.getStatus());
        return repo.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
        student.setStatus(StudentStatus.INACTIVE);
        repo.save(student);
    }
}
