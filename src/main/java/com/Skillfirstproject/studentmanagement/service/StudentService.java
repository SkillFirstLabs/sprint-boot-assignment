package com.Skillfirstproject.studentmanagement.service;

import java.util.Arrays;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.Skillfirstproject.studentmanagement.dto.StudentRequest;
import com.Skillfirstproject.studentmanagement.dto.StudentResponse;
import com.Skillfirstproject.studentmanagement.entity.Student;
import com.Skillfirstproject.studentmanagement.exception.ResourceNotFoundException;
import com.Skillfirstproject.studentmanagement.mapper.StudentMapper;
import com.Skillfirstproject.studentmanagement.repository.StudentRepository;
import com.Skillfirstproject.studentmanagement.specification.StudentSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepo;

    // ✅ CREATE student
    public StudentResponse createStudent(StudentRequest request) {
        Student student = StudentMapper.toEntity(request);
        Student saved = studentRepo.save(student);
        return StudentMapper.toResponse(saved);
    }

    // ✅ GET student by ID
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return StudentMapper.toResponse(student);
    }

    // ✅ UPDATE student
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student existing = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());
        existing.setDateOfBirth(StudentMapper.formatStringToDate(request.getDateOfBirth()));
        existing.setEnrollmentDate(StudentMapper.formatStringToDate(request.getEnrollmentDate()));
        existing.setGpa(request.getGpa());

        Student updated = studentRepo.save(existing);
        return StudentMapper.toResponse(updated);
    }

    // ✅ SOFT DELETE student (set status to INACTIVE)
    public void softDeleteStudent(Long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        student.setStatus(Student.Status.INACTIVE);
        studentRepo.save(student);
    }

    // ✅ GET ALL students with Filtering, Pagination, and Sorting
    public Page<StudentResponse> getAllStudents(int page, int size, String[] sortParams,
                                                String status, Double minGpa, Double maxGpa, String name) {

        // Defensive: handle both array and single string
        Sort sort = Sort.by(Arrays.stream(sortParams)
                .map(param -> {
                    String[] parts = param.split(",");
                    String property = parts[0];
                    String direction = (parts.length > 1) ? parts[1] : "ASC";
                    // Only allow valid property names (avoid "desc" as property)
                    if (property.equalsIgnoreCase("desc") || property.equalsIgnoreCase("asc")) {
                        property = "id"; // fallback to id if property is invalid
                    }
                    return new Sort.Order(Sort.Direction.fromString(direction), property);
                }).toList());

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Student> spec = StudentSpecifications.buildSpecification(status, minGpa, maxGpa, name);

        Page<Student> studentPage = studentRepo.findAll(spec, pageable);
        return studentPage.map(StudentMapper::toResponse);
    }
}
