package com.example.EducationDepartment.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.EducationDepartment.Model.Admin;
import com.example.EducationDepartment.Model.Teacher;

/**
 * 
 * @author RaisAhmad
 * @date 29/10/2021
 * @Discription Admin Repository
 *
 */

public interface AdminRepository extends JpaRepository<Admin, Long> {
	 Admin findByEmail(@Param(value="email") String email);
	 List<Admin> findAllByOrderByDateDesc();
	 Admin findByIdAndEmailTokenAndSmsToken(long id, int emailToken, int smsToken);
	 

}
