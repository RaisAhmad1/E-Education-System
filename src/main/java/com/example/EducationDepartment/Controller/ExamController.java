package com.example.EducationDepartment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Service.ExamService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/exam")
public class ExamController {
	@Autowired
	ExamService examService;
	
	public ExamController(ExamService examService) {
		this.examService = examService;
	}

	

}
