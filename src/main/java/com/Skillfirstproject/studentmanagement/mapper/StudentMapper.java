package com.Skillfirstproject.studentmanagement.mapper;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.Skillfirstproject.studentmanagement.dto.StudentRequest;
import com.Skillfirstproject.studentmanagement.dto.StudentResponse;
import com.Skillfirstproject.studentmanagement.entity.Student;

public class StudentMapper {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Map from DTO to Entity
    public static Student toEntity(StudentRequest dto) {
        return Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .dateOfBirth(LocalDate.parse(dto.getDateOfBirth(), formatter))
                .enrollmentDate(LocalDate.parse(dto.getEnrollmentDate(), formatter))
                .gpa(dto.getGpa())
                .status(Student.Status.ACTIVE)  // default
                .build();
    }

    // Map from Entity to DTO
    public static StudentResponse toResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .dateOfBirth(student.getDateOfBirth().format(formatter))
                .enrollmentDate(student.getEnrollmentDate().format(formatter))
                .gpa(student.getGpa())
                .status(student.getStatus().toString())
                .build();
    }

    public static LocalDate formatStringToDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
    }
}
