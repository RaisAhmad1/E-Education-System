package com.example.EducationDepartment.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Curriculum;
import com.example.EducationDepartment.Model.Department;
import com.example.EducationDepartment.Repository.CurriculumRepository;

@Service

public class CurriculumService {
	private final CurriculumRepository curriculumRepository;

	public CurriculumService(CurriculumRepository curriculumRepository) {

		this.curriculumRepository = curriculumRepository;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public List<Curriculum> listAllUser() {
		return curriculumRepository.findAll();
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param curriculum
	 * @return
	 */
	public Object saveCurriculum(Curriculum curriculum) {

		try {

			Calendar date = Calendar.getInstance();
			curriculum.setDate(date.getTime());
			return ResponseEntity.ok().body(curriculumRepository.save(curriculum));
		} catch (Exception e) {
			return new ResponseEntity<>("User already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param curriculum
	 */

	public void updateCurriculum(Curriculum curriculum) {
		Calendar date = Calendar.getInstance();
		curriculum.setUpdatedDate(date.getTime());
		curriculumRepository.save(curriculum);
	}

}
