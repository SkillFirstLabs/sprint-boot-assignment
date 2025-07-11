package com.student.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.entity.Student;
import com.student.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	@Autowired
	private StudentService studentservice;
	
//	@GetMapping("/hello")
//    public String sayHello() {
//        return "Hi I check if I am working!";
//    }
	
	@PostMapping()
	public ResponseEntity<?> addStudent(@RequestBody Student student) {
		try {
		Student response = studentservice.addStudent(student);
		HashMap<String, Object> map = new HashMap<>();
		map.put("Message ", "Success");
		map.put("Student Data ", response);
//		return ResponseEntity.accepted().body(map);
		return ResponseEntity.status(200).body(map);
		}
		catch(IllegalArgumentException e) {
		return ResponseEntity.status(400).body(e.getMessage());
		}
		catch(RuntimeException e) {
			return ResponseEntity.status(404).body(e.getLocalizedMessage());
		}
	}
	
//	@GetMapping("/page")
//	public ResponseEntity<?> getStudents(@RequestParam(name="pgno", defaultValue = "0") int pgno, @RequestParam(name="rec",defaultValue = "10") int rec) {
//		try {
//			Page<Student> stdnt = studentservice.getStudents(pgno, rec);
//			return ResponseEntity.status(200).body(stdnt);
//		}
//		catch(RuntimeException e) {
//		return ResponseEntity.status(404).body(e.getLocalizedMessage());
//		}
//	}
	
	@GetMapping
	public ResponseEntity<?> getStudents(
			@RequestParam(name = "status", required = false) String status,
		    @RequestParam(name = "mingpa", required = false) Double minGpa,
		    @RequestParam(name = "maxgpa", required = false) Double maxGpa,
		    @RequestParam(name = "name", required = false) String name,
			@PageableDefault(page=0, size=10) Pageable pageable) {
		try {
			Page<Student> stdnt = studentservice.getStudents(status, minGpa, maxGpa, name, pageable);
			return ResponseEntity.status(200).body(stdnt);
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.status(400).body(e.getMessage());
			}
		
		catch(RuntimeException e) {
		return ResponseEntity.status(404).body(e.getLocalizedMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getSingleStudent(@PathVariable("id") Long id) {
		try {
			Student stdnt = studentservice.getStudentById(id);
			return ResponseEntity.status(200).body(stdnt);
		}
		catch(RuntimeException e) {
		return ResponseEntity.status(404).body(e.getLocalizedMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
		try {
			Student stdnt = studentservice.updateStudent(id, student);
			return ResponseEntity.status(200).body(stdnt);
//					.body(stdnt);
		}
		catch(RuntimeException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
		try {
//			Student stdnt = ;
			studentservice.deleteStudent(id);
			return ResponseEntity.status(204).build();
		}
		catch(RuntimeException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}
	
	
}