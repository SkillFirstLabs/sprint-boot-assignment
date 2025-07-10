package com.example.StudentManagementSystem.Repository;

import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.enums.Status;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long>, JpaSpecificationExecutor<Student> {
    List<Student> findByStatus(Status status) ;
    List<Student>findByGpaBetween(Double minGpa,Double maxGpa);
    List<Student>findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName,String lastName);

}
