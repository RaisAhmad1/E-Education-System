package com.example.EducationDepartment.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Model.Teacher;

/**
 * 
 * @author RaisAhmad
 * @date 29/10/2021
 * @Discription Student Repository
 *
 */

public interface StudentRepository extends JpaRepository<Student, Long> {
	 Student findByEmail(@Param(value="email") String email);
	 List<Student> findAllByStatus(boolean status);
	 Optional<Student> findByCnic(String cnic);
	 Optional<Student> findByCnicAndDegree(String cnic, String degree);
	 List<Student> findAllByOrderByDateDesc();
//	 Optional<Student> findByIdAndEmailTokenAndSmsToken(long id, int emailToken, int smsToken);
	 Student findByIdAndEmailTokenAndSmsToken(long id, int emailToken, int smsToken);
}
