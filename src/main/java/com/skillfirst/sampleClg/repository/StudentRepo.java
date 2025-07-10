package com.skillfirst.sampleClg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillfirst.sampleClg.model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    
    Page<Student> findByStatus(String status, Pageable pageable);

    Page<Student> findByGpaGreaterThanEqual(Double gpa, Pageable pageable);

    Page<Student> findByStatusAndGpaGreaterThanEqual(String status, Double gpa, Pageable pageable);
}
