package com.example.StudentManagementSystem.ServiceImpl;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.enums.Status;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {
    public static Specification<Student> filterStudents(Status status, Double minGpa, Double maxGpa, String keyword) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (minGpa != null && maxGpa != null) {
                predicates.add(cb.between(root.get("gpa"), minGpa, maxGpa));
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                Predicate firstNameLike = cb.like(cb.lower(root.get("firstName")), "%" + keyword.toLowerCase() + "%");
                Predicate lastNameLike = cb.like(cb.lower(root.get("lastName")), "%" + keyword.toLowerCase() + "%");
                predicates.add(cb.or(firstNameLike, lastNameLike));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

