package com.example.EducationDepartment.Controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Model.Degree;
import com.example.EducationDepartment.Service.DegreeService;
import com.example.EducationDepartment.Service.ResultService;
import com.example.EducationDepartment.Service.DegreeService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/degree")
public class DegreeController {
	@Autowired
	DegreeService degreeService;
	ResultService resultService;
	private static boolean isLogin = false;


	public DegreeController(DegreeService degreeService, ResultService resultService) {
		this.degreeService = degreeService;
		this.resultService = resultService;
	}

		

	
}
