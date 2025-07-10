package com.Skillfirstproject.studentmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @NotNull(message = "Enrollment date is required")
    private LocalDate enrollmentDate;

    @DecimalMin(value = "0.0", message = "GPA must be at least 0.0")
    @DecimalMax(value = "10.0", message = "GPA must be at most 10.0")
    private Double gpa;

    public enum Status {
        ACTIVE, INACTIVE
    }
}
