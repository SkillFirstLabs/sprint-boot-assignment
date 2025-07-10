package com.Skillfirstproject.studentmanagement.specification;

import com.Skillfirstproject.studentmanagement.entity.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecifications {

    public static Specification<Student> hasStatus(String status) {
        return (root, query, cb) -> status == null ? null :
                cb.equal(root.get("status"), Student.Status.valueOf(status.toUpperCase()));
    }

    public static Specification<Student> gpaBetween(Double min, Double max) {
        return (root, query, cb) -> {
            if (min != null && max != null)
                return cb.between(root.get("gpa"), min, max);
            if (min != null)
                return cb.greaterThanOrEqualTo(root.get("gpa"), min);
            if (max != null)
                return cb.lessThanOrEqualTo(root.get("gpa"), max);
            return null;
        };
    }

    public static Specification<Student> nameContains(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.or(
                    cb.like(cb.lower(root.get("firstName")), "%" + name.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("lastName")), "%" + name.toLowerCase() + "%")
                );
    }

    public static Specification<Student> buildSpecification(String status, Double minGpa, Double maxGpa, String name) {
        return Specification
                .where(hasStatus(status))
                .and(gpaBetween(minGpa, maxGpa))
                .and(nameContains(name));
    }
}

