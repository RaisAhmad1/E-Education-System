package com.example.EducationDepartment.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Model.Institution;
import com.example.EducationDepartment.Service.InstitutionService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EnableSwagger2
@RestController
@RequestMapping("/institution")
public class InstitutionController {
	private static final Logger LOG = LogManager.getLogger(InstitutionController.class);
	@Autowired
	InstitutionService institutionService;

	public InstitutionController(InstitutionService institutionService) {
		this.institutionService = institutionService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allInstitutions")

	public ResponseEntity<Object> institutionList() {

		List<Institution> institutionList = institutionService.listAllInstitutions();

		if (institutionList.isEmpty()) {
			LOG.info("List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("List of institutions : " + institutionList);
			return new ResponseEntity<>(institutionList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 * @return
	 */
	@PostMapping("/addInstitution")

	public ResponseEntity<String> addInstitution(@Valid @RequestBody Institution institution) {

		institutionService.saveInstitution(institution);
		LOG.info("Institution added successfully : " + institution);
		return new ResponseEntity<>("Institution added successfully", HttpStatus.OK);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 * @return
	 */

	@PutMapping("/updateInstitute")
	public ResponseEntity<Object> updateInstitution(@RequestBody Institution institution) {

		try {
			institutionService.updateInstitution(institution);
			LOG.info("Category updated successfully:  " + institution);
			return new ResponseEntity<>("Institution updated successfully ", HttpStatus.OK);
		} catch (NoSuchElementException e) {
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<>("Institution not found incorrect id ", HttpStatus.NOT_FOUND);
		}

	}
	
}
