package com.example.EducationDepartment.Controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Model.Teacher;
import com.example.EducationDepartment.Service.ResultService;
import com.example.EducationDepartment.Service.TeacherService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/teacher")
public class TeacherController {
	private static final Logger LOG = LogManager.getLogger(TeacherController.class);
	@Autowired
	TeacherService teacherService;
	ResultService resultService;
	private static boolean isLogin = false;
	private static long idd;

	public TeacherController(TeacherService teacherService, ResultService resultService) {
		this.teacherService = teacherService;
		this.resultService = resultService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param paramEmail
	 * @param paramPassword
	 * @return
	 */
	@GetMapping("/login")

	// Comparing Email and password of user from database

	public ResponseEntity<Object> login(@RequestParam(value = "Email") String paramEmail,
			@RequestParam(value = "password") String paramPassword) {
		Teacher teacher = teacherService.getEmail(paramEmail);

		if (paramEmail.equals(teacher.getEmail()) && paramPassword.equals(teacher.getPassword())) {
			isLogin = true;
			idd = teacher.getId();
			LOG.info("Login successfully ");
			return new ResponseEntity<>("login successfully", HttpStatus.OK);
		} else {
			LOG.info("Incorrect details ");
			System.out.println(teacher.getEmail() + "  name is " + teacher.getFirstName() + "id is " + teacher.getId());
			return new ResponseEntity<>("Incorrect login details ", HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param teacher
	 * @return
	 */
	@PostMapping("/RegisterTeacher")

	public ResponseEntity<Object> RegisterTeacher(@RequestBody Teacher teacher) {

	
		return teacherService.saveTeacher(teacher);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param result
	 * @return
	 */
	@PostMapping("/addResult")

	public ResponseEntity<String> addResult(@RequestBody Result result) {

        if (isLogin) {
		teacherService.saveResult(result);
		LOG.info("result added successfully : " + result);
		return new ResponseEntity<>("Result added successfully", HttpStatus.OK);

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/teacherPersonalDetails")

	public ResponseEntity<Object> PersonalDetails() {
		if (isLogin) {
			try {
				ResponseEntity<Object> teacher = teacherService.getTeacherId(idd);

				return new ResponseEntity<>(teacher.getBody(), HttpStatus.FOUND);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>("User not found incorrect id ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @return
	 */

	@GetMapping("/getResult/{id}")

	public ResponseEntity<Object> getResultById(@RequestHeader long id) {

		if (isLogin) {

			try {
				Result result = teacherService.getResult(id);
				LOG.info("Result found: " + result);
				return new ResponseEntity<>(result, HttpStatus.FOUND);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>("User not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param result
	 * @return
	 */
	@PutMapping("/updateResult")

	public ResponseEntity<Object> updateResut(@RequestBody Result result) {

		if (isLogin) {

			try {

				teacherService.updateStudentMarks(result);
				LOG.info("Result updated successfully : " + result);
				return new ResponseEntity<>("Result updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>("Result not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param teacher
	 * @param firstName
	 * @param lastName
	 * @return
	 */

	@PutMapping("/updateTeacher'sName")

	public ResponseEntity<Object> updateName(@RequestBody Teacher teacher, @RequestHeader String firstName,
			@RequestHeader String lastName) {

		if (isLogin) {

			try {
				teacherService.updateTeacherName(teacherService.getTeacher(idd), firstName, lastName);
				LOG.info("Teacher's name updated successfully ");
				return new ResponseEntity<>("Teacher's Name updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>("Teacher not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @param password
	 * @return
	 */

	@PutMapping("/updateTeacher'sPassword")

	public ResponseEntity<Object> updatePassword(@RequestBody Student student, @RequestHeader String password) {

		if (isLogin) {

			try {
				teacherService.updateTeacherPassword(teacherService.getTeacher(idd), password);
				LOG.info("Teacher's password updated successfully ");
				return new ResponseEntity<>("Teacher's password updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>("Teacher not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @param emailToken
	 * @param smsToken
	 * @return
	 */
	@GetMapping("/Teacher'sAccountVerification")
	public ResponseEntity<Object> verifyAccount(@RequestHeader long id, @RequestHeader int emailToken,
			@RequestHeader int smsToken) {

		try {

			return teacherService.verify(id, emailToken, smsToken);

		} catch (NoSuchElementException e) {

			return new ResponseEntity<>("Teacher not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 2/11/2021
	 * @param id
	 * @return
	 */
	@PutMapping("/sendToken/{id}")
	public ResponseEntity<Object> sendVerificationToken(@RequestHeader long id) {

		try {

			return teacherService.sendTokens(id);

		} catch (NoSuchElementException e) {

			return new ResponseEntity<>("Teacher not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @param phone
	 * @return
	 */
	@PutMapping("/updateTeacher'sPhone")

	public ResponseEntity<Object> updatePhone(@RequestBody Teacher teacher, @RequestHeader String phone) {

		if (isLogin) {

			try {
				teacherService.updateTeacherPhone(teacherService.getTeacher(idd), phone);
				LOG.info("Teacher's phone number updated successfully ");
				return new ResponseEntity<>("Teacher's phone number updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Teacher not found ");
				return new ResponseEntity<>("Teacher not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}


	
//	@GetMapping("/criminalStatusViaAPI")
//	public boolean criminalStatus() {
//		
//		return teacherService.checkCriminalRecord1();
//				
//	}

	// Fawad's API

//	@GetMapping("/bank")
//	public String bankAPI() {
//		
//		return teacherService.bankAPI();
//				
//	}
//	
//	@GetMapping("/Currency")
//	public ResponseEntity<Object> currency() {
//		
//		return new ResponseEntity<Object>( teacherService.getAllCurrency(), HttpStatus.OK);
//				
//	}
}
