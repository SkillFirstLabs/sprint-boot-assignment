package com.student.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	public boolean existsByEmail(String email);

	public Page<Student> findByGpaBetween(Double minGpa, Double maxGpa, Pageable pageable);

	public Page<Student> findByStatusIgnoreCase(String status, Pageable pageable);

	public Page<Student> findByStatusIgnoreCaseAndGpaBetween(String status, Double mingpa, Double maxgpa, Pageable pageable);

	public Page<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String name, String name2,
			Pageable pageable);

	public Page<Student> findByStatusIgnoreCaseAndGpaGreaterThanEqual(String status, Double mingpa, Pageable pageable);

	public Page<Student> findByStatusIgnoreCaseAndGpaLessThanEqual(String status, Double maxgpa, Pageable pageable);
}