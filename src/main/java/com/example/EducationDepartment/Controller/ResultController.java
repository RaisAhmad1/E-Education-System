package com.example.EducationDepartment.Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Service.ResultService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/result")
public class ResultController {
	private static final Logger LOG = LogManager.getLogger(ResultController.class);
	@Autowired
	ResultService resultService;

	public ResultController(ResultService resultService) {
		this.resultService = resultService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allResults")

	public ResponseEntity<Object> allResults() {

		return (ResponseEntity<Object>) resultService.listAllResults();

	}

}
