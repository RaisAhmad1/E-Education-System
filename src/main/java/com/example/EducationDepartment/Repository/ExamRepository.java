package com.example.EducationDepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EducationDepartment.Model.Exam;

/**
 * 
 * @author Rais Ahmad
 * @date 29/10/2021
 * @Discription Exam Repository
 *
 */

public interface ExamRepository extends JpaRepository<Exam, Long>{

}
