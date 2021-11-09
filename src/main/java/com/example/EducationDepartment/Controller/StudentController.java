package com.example.EducationDepartment.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EducationDepartment.Model.Admin;
import com.example.EducationDepartment.Model.Department;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Model.Teacher;
import com.example.EducationDepartment.Service.StudentService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/student")
public class StudentController {

	private static final Logger LOG = LogManager.getLogger(DepartmentController.class);

	@Autowired
	StudentService studentService;
	private static boolean isLogin = false;
	private static long idd;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
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
		Student student = studentService.getEmail(paramEmail);

		if (paramEmail.equals(student.getEmail()) && paramPassword.equals(student.getPassword())) {
			isLogin = true;
			idd = student.getId();
			LOG.info("Login Successfully  ");

			return new ResponseEntity<>("login successfully", HttpStatus.OK);
		} else {
			System.out.println(student.getEmail() + "  name is " + student.getFirstName() + "id is " + student.getId());
			LOG.info("Incorrec details ");
			return new ResponseEntity<>("Incorrect login details ", HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allStudents")

	public ResponseEntity<Object> studentList() {

		return (ResponseEntity<Object>) studentService.listAllStudentsByDate();

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @return
	 */
	@PostMapping("/studentRegister")

	public ResponseEntity<Object> studentRegistration(@RequestBody Student student) {

		LOG.info("Student added successfully : " + student);
		return studentService.saveStudent(student);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/studentPersonalDetails")

	public ResponseEntity<Object> PersonalDetails() {
		if (isLogin) {
			try {
				ResponseEntity<Object> student = studentService.getStudentId(idd);
				LOG.info("Student : " + student);
				return new ResponseEntity<>(student.getBody(), HttpStatus.FOUND);
			} catch (NoSuchElementException e) {
				LOG.info("Student not found ");
				return new ResponseEntity<>("Student not found, incorrect id ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	@PutMapping("/updateStudent'sName")

	public ResponseEntity<Object> updateName(@RequestBody Student student, @RequestHeader String firstName,
			@RequestHeader String lastName) {

		if (isLogin) {

			try {
				studentService.updateStudent(studentService.getStudent(idd), firstName, lastName);
				LOG.info("Student's name updated successfully ");
				return new ResponseEntity<>("Student's name updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Student not found ");
				return new ResponseEntity<>("Student not found incorrect id ", HttpStatus.NOT_FOUND);
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
	@PutMapping("/updateStudent'sPassword")

	public ResponseEntity<Object> updatePassword(@RequestBody Student student, @RequestHeader String password) {

		if (isLogin) {

			try {
				studentService.updateStudentPassword(studentService.getStudent(idd), password);
				LOG.info("Student's password updated successfully ");
				return new ResponseEntity<>("Student's password updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Student not found ");
				return new ResponseEntity<>("Student not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @param phone
	 * @return
	 */
	@PutMapping("/updateStudent'sPhone")

	public ResponseEntity<Object> updatePhone(@RequestBody Student student, @RequestHeader String phone) {

		if (isLogin) {

			try {
				studentService.updateStudentPhone(studentService.getStudent(idd), phone);
				LOG.info("Student's phone number updated successfully ");
				return new ResponseEntity<>("Student's phone number updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Student not found ");
				return new ResponseEntity<>("Student not found incorrect id ", HttpStatus.NOT_FOUND);
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
	@GetMapping("/Verification")
	public ResponseEntity<Object> verifyAccount(@RequestHeader long id, @RequestHeader int emailToken,
			@RequestHeader int smsToken) {

		try {

			return studentService.verify(id, emailToken, smsToken);

		} catch (NoSuchElementException e) {

			return new ResponseEntity<>("Student not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/sendToken/{id}")
	public ResponseEntity<Object> sendToken(@RequestHeader long id) {

		try {

			return studentService.sendTokens(id);

		} catch (NoSuchElementException e) {

			return new ResponseEntity<>("Student not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 */
	@DeleteMapping("/{id}")

	public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {

		return studentService.deleteStudent(id);

	}

	/**
	 * @author RaisAhmad
	 * @date 4/11/2021
	 * @return
	 */
	@GetMapping("/getResultByStudent")
	public ResponseEntity<Object> getResultByStudentId() {
		if (isLogin) {
			try {

				System.out.println("id is " + idd);
				ResponseEntity<Object> student = studentService.getResultByStudentId(idd);

				return new ResponseEntity<>(student.getBody(), HttpStatus.FOUND);
			} catch (NoSuchElementException e) {
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<>("Student not found incorrect id ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 4/11/2021
	 * @param id
	 * @return
	 */
	@GetMapping("/degreeVerification")
	public ResponseEntity<Object> verifyDegree(@RequestHeader long id) {
		if (isLogin) {
			try {

				return studentService.verifyDegree(id);

			} catch (NoSuchElementException e) {

				return new ResponseEntity<>("Degree not found", HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/cnicVerification")
	public boolean verifyCNIC(@RequestHeader String cnic) {

		return studentService.verifyCNIC(cnic);

	}

	@GetMapping("/qualificationVerification")
	public boolean verifyQualification(@RequestHeader String cnic, @RequestHeader String name) {

		return studentService.verifyQualification(cnic, name);

	}

}
