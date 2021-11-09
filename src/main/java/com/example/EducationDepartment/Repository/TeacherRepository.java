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
 * @Discription Teacher Repository
 * 
 */

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	 Teacher findByEmail(@Param(value="email") String email);
	 List<Teacher> findAllByStatus(boolean status);
	 List<Teacher> findAllByOrderByDateDesc();
	 Teacher findByIdAndEmailTokenAndSmsToken(long id, int emailToken, int smsToken);
	
}
