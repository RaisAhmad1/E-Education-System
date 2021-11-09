package com.example.EducationDepartment.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EducationDepartment.Model.Degree;

/**
 * 
 * @author Rais Ahmad
 * @date 29/10/2021
 * @Discription Degree Repository
 *
 */

public interface DegreeRepository extends JpaRepository<Degree, Long>{

	Optional<Degree> findByName(String name);
}
