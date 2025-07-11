package com.srinath.student_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srinath.student_management.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Custom method for name-based search (used in service if needed)
    List<Student> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(String firstName, String lastName);
}
