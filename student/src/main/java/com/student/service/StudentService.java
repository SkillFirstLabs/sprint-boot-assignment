	package com.student.service;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.student.entity.Student;
import com.student.repository.StudentRepository;
	
	@Service
	public class StudentService {
		
		@Autowired
		private StudentRepository studentrepo;
		
		public Student addStudent(Student student) {
			if(student.getFirstName()==null || student.getFirstName().isBlank()) {
				throw new IllegalArgumentException("firstName is required");
			}
			else if(student.getLastName()==null || student.getLastName().isBlank()) {
				throw new IllegalArgumentException("lastName is required");
			}
			else if(student.getEmail()==null || student.getEmail().isBlank()) {
				throw new IllegalArgumentException("Email is required");
			}
			else if(studentrepo.existsByEmail(student.getEmail())) {
				throw new IllegalArgumentException("Email already exists");
			}
			else if (student.getDateOfBirth() == null) {
			    throw new IllegalArgumentException("Date of birth is required");
			}
			else if(!student.getEmail().endsWith("@gmail.com")) { 
				throw new IllegalArgumentException("Properly define your email"); 
				}

//			if(studentrepo.existsByEmail(student.getEmail())) {
//				throw new IllegalArgumentException("Email already exists");
//			}
			if(student.getGpa()!=null && (student.getGpa()<0.0 || student.getGpa()>10.0)) {
				throw new IllegalArgumentException("Enter GPA in range from 0.0 to 10.0 only");
			}
			
			return studentrepo.save(student);
		}

		public Student getStudentById(Long id) {
			return studentrepo.findById(id)
		            .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
		}

		public Student updateStudent(Long id, Student student) {
			Student s = studentrepo.findById(id).orElseThrow(()->new RuntimeException("Student not found with this id to update"));
			if(student.getFirstName()!=null && student.getFirstName().isBlank()) {
				throw new IllegalArgumentException("Update firstName  properly");
			}
			else if(student.getLastName()!=null && student.getLastName().isBlank()) {
				throw new IllegalArgumentException("Update lastName  properly");
			}
			else if (student.getEmail() != null && student.getEmail().isBlank() && !student.getEmail().endsWith("@gmail.com")) {
		        throw new IllegalArgumentException("Update email properly using @gmail.com");
		    }
			else if(student.getGpa()!=null && (student.getGpa()<0.0 || student.getGpa()>10.0) ) {
				throw new IllegalArgumentException("Update GPA properly within range 0.0 to 10.0");
			}
			
			if (student.getFirstName()!=null) {
		        s.setFirstName(student.getFirstName());
		    }
		    if (student.getLastName()!=null) {
		        s.setLastName(student.getLastName());
		    }
		    if (student.getEmail()!=null) {
		        s.setEmail(student.getEmail());
		    }
		    if(student.getStatus()!=null) {
		    	if (!(student.getStatus().equalsIgnoreCase("ACTIVE") || student.getStatus().equalsIgnoreCase("INACTIVE"))) {
		            throw new IllegalArgumentException("Status must be either ACTIVE or INACTIVE");
		        }
		    	s.setStatus(student.getStatus().toUpperCase());
		    }
		    if(student.getEnrollmentDate()!=null) {
		    	s.setEnrollmentDate(student.getEnrollmentDate());
		    }
		    if(student.getDateOfBirth()!=null) {
		    	s.setDateOfBirth(student.getDateOfBirth());
		    }
		    if(student.getGpa()!=null && student.getGpa()>0.0 && student.getGpa()<10.0 ) {
		    	s.setGpa(student.getGpa());
		    }
			
			return studentrepo.save(s);
		}

		public String deleteStudent(Long id) {
			Student s = studentrepo.findById(id).orElseThrow(()-> new RuntimeException("No user found with this ID"));
			s.setStatus("INACTIVE");
			studentrepo.save(s);
			return "Successfully changed the status of the student";
		}

		public Page<Student> getStudents(int p, int r) {
			Pageable pageable = PageRequest.of(p, r);
			Page<Student> al = studentrepo.findAll(pageable);
			return al;
		}
		
		public Page<Student> getStudents(String status, Double mingpa, Double maxgpa,String name, Pageable pageable) {
			
			if (status != null && mingpa != null && maxgpa == null) {
			    return studentrepo.findByStatusIgnoreCaseAndGpaGreaterThanEqual(status, mingpa, pageable);
			}
			else if (status != null && maxgpa != null && mingpa == null) {
			    return studentrepo.findByStatusIgnoreCaseAndGpaLessThanEqual(status, maxgpa, pageable);
			}
			
			else if (status != null && mingpa != null && maxgpa != null) {
			    return studentrepo.findByStatusIgnoreCaseAndGpaBetween(status, mingpa, maxgpa, pageable);
			}
			if(status!=null) {
				if(!(status.equalsIgnoreCase("ACTIVE") || status.equalsIgnoreCase("INACTIVE")))throw new IllegalArgumentException("Enter status either ACTIVE or INACTIVE");
				return studentrepo.findByStatusIgnoreCase(status, pageable);
			}
			else if(mingpa!=null && maxgpa!=null) {
				if(mingpa<0 || maxgpa>10)throw new IllegalArgumentException("Enter GPA range properly from 0.0 to 10.0");
				return studentrepo.findByGpaBetween(mingpa, maxgpa, pageable);
			}
			else if(status!=null && mingpa!=null)
				return studentrepo.findByStatusIgnoreCaseAndGpaGreaterThanEqual(status, mingpa, pageable);
			else if(status!=null && maxgpa!=null)
				return studentrepo.findByStatusIgnoreCaseAndGpaLessThanEqual(status, maxgpa, pageable);
			else if(name!=null) {
				return studentrepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name, pageable);
			}
//			
//			Pageable pageable = PageRequest.of(p, r);
//			
			else
				return studentrepo.findAll(pageable);
		}
	}