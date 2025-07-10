package com.student.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;

@Entity
@Table(name="Student")
public class Student {
	
//	public StudentEntity(Long id, String firstName, String lastName, String email, LocalDate dateOfBirth, String status,
//			LocalDate enrollmentDate, Double gpa) {
//		super();
//		Id = id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.dateOfBirth = dateOfBirth;
//		this.status = status;
//		this.enrollmentDate = enrollmentDate;
//		this.gpa = gpa;
//	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "firstName", nullable = false, unique = false)
	private String firstName;
	
	@Column(name = "lastName", nullable = false, unique = false)
	private String lastName;
	
    public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public Double getGpa() {
		return gpa;
	}

	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	@Column(name = "email", unique = true, nullable = false)
	private String email;
    
    @Column(nullable = false, name = "DOB")
    @Past(message = "Date of Birth must be in the past")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    
    @Column(nullable = false)
    private String status = "ACTIVE";
	
    @Column(name = "enrollmentDate")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate enrollmentDate;
    
    @Column(name="GPA")
    private Double gpa;
}