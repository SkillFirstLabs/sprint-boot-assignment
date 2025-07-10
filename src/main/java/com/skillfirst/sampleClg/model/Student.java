package com.skillfirst.sampleClg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "first name is required")
    @Size(min = 2,max = 15,message = "the first name should be of length 2 and 15")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(min = 2,max = 10,message = "the last name should be of length 2 and 10")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "Date of birth is required")
    @Pattern(
        regexp = "\\d{2}-\\d{2}-\\d{4}",
        message = "Date of birth must be in DD-MM-YYYY format"
    )
    private String dateOfBirth; 

    @NotBlank(message = "Status is required")
    private String status ;

    @NotBlank(message = "Enrollment date is required")
    @Pattern(
        regexp = "\\d{2}-\\d{2}-\\d{4}",
        message = "Enrollment date must be in DD-MM-YYYY format"
    )
    private String enrollmentDate; 

    @PositiveOrZero(message = "GPA must be zero or positive")
    private double gpa;
}
