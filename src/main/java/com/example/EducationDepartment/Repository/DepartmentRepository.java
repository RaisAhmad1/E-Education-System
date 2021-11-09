package com.example.EducationDepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EducationDepartment.Model.Department;

/**
 * 
 * @author Rais Ahmad
 * @date 29/10/2021
 * @Discription Department Repository
 *
 */

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
