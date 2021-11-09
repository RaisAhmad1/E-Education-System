package com.example.EducationDepartment.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Institution;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Repository.InstitutionRepository;

@Service

public class InstitutionService {
	private final InstitutionRepository institutionRepository;

	public InstitutionService(InstitutionRepository institutionRepository) {

		this.institutionRepository = institutionRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	public List<Institution> listAllInstitutions() {
		return institutionRepository.findAll();
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 * @return
	 */
	public Object saveInstitution(Institution institution) {

		try {

			Calendar date = Calendar.getInstance();
			institution.setDate(date.getTime());
			

			if (institution.getName() == null) {
				return new ResponseEntity<>("Institution name can't be empty", HttpStatus.OK);
			}else {
			return ResponseEntity.ok().body(institutionRepository.save(institution));
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Institution already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 */

	public void updateInstitution(Institution institution) {
		Calendar date = Calendar.getInstance();
		institution.setDate(date.getTime());
		institutionRepository.save(institution);
	}

}
