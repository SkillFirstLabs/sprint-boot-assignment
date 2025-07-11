package com.student.studentmanagement.specification;

import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.model.StudentStatus;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> hasStatus(String status) {
        return (root, query, cb) ->
            status == null ? null : cb.equal(root.get("status"), StudentStatus.valueOf(status.toUpperCase()));
    }

    public static Specification<Student> hasMinGpa(Double minGpa) {
        return (root, query, cb) ->
            minGpa == null ? null : cb.greaterThanOrEqualTo(root.get("gpa"), minGpa);
    }

    public static Specification<Student> hasMaxGpa(Double maxGpa) {
        return (root, query, cb) ->
            maxGpa == null ? null : cb.lessThanOrEqualTo(root.get("gpa"), maxGpa);
    }

    public static Specification<Student> nameContains(String name) {
        return (root, query, cb) ->
            name == null ? null : cb.or(
                cb.like(cb.lower(root.get("firstName")), "%" + name.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("lastName")), "%" + name.toLowerCase() + "%")
            );
    }
}
