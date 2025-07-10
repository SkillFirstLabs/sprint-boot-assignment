package com.example.student.spec;

import com.example.student.model.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecifications {
    public static Specification<Student> hasStatus(Student.Status status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Student> gpaBetween(Double min, Double max) {
        return (root, query, cb) -> cb.between(root.get("gpa"), min, max);
    }

    public static Specification<Student> nameContains(String name) {
        return (root, query, cb) -> cb.or(
            cb.like(cb.lower(root.get("firstName")), "%" + name.toLowerCase() + "%"),
            cb.like(cb.lower(root.get("lastName")), "%" + name.toLowerCase() + "%")
        );
    }
}