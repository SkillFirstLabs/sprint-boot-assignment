package com.example.StudentManagementSystem.ServiceImpl;

import com.example.StudentManagementSystem.Repository.StudentRepository;
import com.example.StudentManagementSystem.Services.StudentService;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentrepository;
    @Override
    public Student createStudent(Student student) {
        return studentrepository.save(student);
    }
    public Student getStudentById(Long id){
        return studentrepository.findById(id).orElseThrow(()->new RuntimeException("Student not found with id:"+ id) );
    }
    @Override
    public Student save(Student student){
        return studentrepository.save(student);
    }
    @Override
    public Student softDeleteStudent(Long id){
      Student student =
    studentrepository.findById(id).orElseThrow(()->new RuntimeException("Student id not found with id:"+id));
    student.setStatus(Status.INACTIVE);
    return studentrepository.save(student);
    }
    @Override
    public List<Student> getStudentsByStatus(Status status) {
        return studentrepository.findByStatus(status);
    }
    @Override
    public List<Student> getStudentsByGpaBetween(Double minGpa,Double maxGpa){
        return studentrepository.findByGpaBetween(minGpa,maxGpa);
    }
    @Override
    public List<Student> searchStudentsByName(String name) {
        return studentrepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }
    @Override
    public Page<Student> getStudents(Status status, Double minGpa, Double maxGpa,
                                     String keyword, int page, int size, List<Sort.Order> sortOrders) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrders));
        Specification<Student> specification = StudentSpecification.filterStudents(status, minGpa, maxGpa, keyword);
        return studentrepository.findAll(specification, pageable);
    }





}
