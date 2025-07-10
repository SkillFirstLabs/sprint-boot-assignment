package com.example.StudentManagementSystem.Services;

import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);
    Student getStudentById(Long id);
    Student save(Student student);
    Student softDeleteStudent(Long id);
    List<Student> getStudentsByStatus(Status status);
    List<Student> getStudentsByGpaBetween(Double minGpa,Double maxGpa);
    List<Student>searchStudentsByName(String Name);
    Page<Student> getStudents(Status status, Double minGpa, Double maxGpa, String keyword,
                              int page, int size, List<Sort.Order> sortOrders);


}
