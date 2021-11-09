package com.example.EducationDepartment.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Repository.ResultRepository;
import com.example.EducationDepartment.Repository.StudentRepository;

@Service
public class ResultService {
	private static final Logger LOG = LogManager.getLogger(ResultService.class);
private final ResultRepository resultRepository;
	
	public ResultService(ResultRepository resultRepository) {
	
		this.resultRepository = resultRepository;
	}
		/**
		 * @author RaisAhmad
		 * @date 1/11/2021
		 * @return
		 */
	 public ResponseEntity<Object> listAllResults() {
	        List<Result> resultList = resultRepository.findAll();
	        if (resultList.isEmpty()) {
	        	LOG.info("Result List is empty ");
				return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
			} else {
				LOG.info("Result list :  " + resultList);
				return new ResponseEntity<>(resultList, HttpStatus.OK);
			}
	    }
	
}
