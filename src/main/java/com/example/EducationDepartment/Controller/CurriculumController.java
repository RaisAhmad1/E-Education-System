package com.example.EducationDepartment.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Model.Curriculum;
import com.example.EducationDepartment.Model.Department;
import com.example.EducationDepartment.Service.CurriculumService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/curriculum")
public class CurriculumController {
	private static final Logger LOG = LogManager.getLogger(DepartmentController.class);
	@Autowired
	CurriculumService curriculumService;

	public CurriculumController(CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	@GetMapping("/allCurriculum")

	public ResponseEntity<Object> curriculumList() {

		List<Curriculum> userList = curriculumService.listAllUser();

		if (userList.isEmpty()) {
			LOG.info("List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("List of Curriculum : " + userList);
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 */
	@PostMapping("/addCurriculum")

	public ResponseEntity<String> addCurriculum(@RequestBody Curriculum curriculum) {

		curriculumService.saveCurriculum(curriculum);
		LOG.info("Curriculum added successfully  " + curriculum);
		return new ResponseEntity<>("Curriculum added successfully", HttpStatus.OK);
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param curriculum
	 * @return
	 */
	@PutMapping("/updateCurriculum")
	public ResponseEntity<Object> updateCurriculum(@RequestBody Curriculum curriculum) {

		try {
			curriculumService.updateCurriculum(curriculum);
			LOG.info("Curriculum updated successfully:  " + curriculum);
			return new ResponseEntity<>("Curriculum info updated successfully ", HttpStatus.OK);
		} catch (NoSuchElementException e) {
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<>("Curriculum not found incorrect id ", HttpStatus.NOT_FOUND);
		}

	}

}
