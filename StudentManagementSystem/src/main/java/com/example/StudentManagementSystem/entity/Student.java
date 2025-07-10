package com.example.StudentManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.example.StudentManagementSystem.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="First Name is required")
    private String firstName;
    @NotBlank(message="Last name is required")
    private String lastName;
    @NotBlank(message="Email  is required")
    @Email(message="email must be valid")
    private String email;
    @NotNull(message="Date of birth is required")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @NotNull(message = "Enrollment date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate enrollmentDate;

    @NotNull(message = "GPA is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "GPA must be >= 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "GPA must be <= 10.0")
    private Double gpa;


    public Status getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}
