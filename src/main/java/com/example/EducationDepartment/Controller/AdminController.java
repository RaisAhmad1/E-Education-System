package com.example.EducationDepartment.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.example.EducationDepartment.Model.Curriculum;
import com.example.EducationDepartment.Model.Degree;
import com.example.EducationDepartment.Model.Department;
import com.example.EducationDepartment.Model.Exam;
import com.example.EducationDepartment.Model.Institution;
import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Model.Teacher;
import com.example.EducationDepartment.Service.AdminService;
import com.example.EducationDepartment.Service.ResultService;
import com.example.EducationDepartment.Service.StudentService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final Logger LOG = LogManager.getLogger(AdminController.class);
	AdminService adminService;
	private static boolean isLogin = false;
	private static long idd;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param paramEmail
	 * @param paramPassword
	 * @return
	 */

	@GetMapping("/adminLogin")

	// Comparing Email and password of user from database

	public ResponseEntity<Object> login(@RequestParam(value = "Email") String paramEmail,
			@RequestParam(value = "password") String paramPassword) {
		Admin admin = adminService.getEmail(paramEmail);

		if (paramEmail.equals(admin.getEmail()) && paramPassword.equals(admin.getPassword())) {
			isLogin = true;
			idd = admin.getId();
			LOG.info("Login Successfully ");
			return new ResponseEntity<>("login successfully", HttpStatus.OK);
		} else {
			System.out.println(admin.getEmail() + "  name is " + admin.getFirstName() + "id is " + admin.getId());
			return new ResponseEntity<>("Incorrect login details ", HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allStudent'sList")

	public ResponseEntity<Object> studentList() {

		if (isLogin) {
			
			return (ResponseEntity<Object>) adminService.listAllStudentsByDate();

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	@GetMapping("/allVerifiedStudent'sList")

	public ResponseEntity<Object> verifiedStudentsList() {

		if (isLogin) {
			return (ResponseEntity<Object>) adminService.listAllVerifiedStudents();

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	@GetMapping("/allTeachers")

	public ResponseEntity<Object> teacherList() {

		if (isLogin) {

			return (ResponseEntity<Object>) adminService.listAllTeachersByDate();

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	@GetMapping("/allVerifiedTeachers")

	public ResponseEntity<Object> verifiedTeachersList() {

		if (isLogin) {

			return (ResponseEntity<Object>) adminService.listAllVerifiedTeachers();

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/allAdmins")

	public ResponseEntity<Object> adminList() {

		return (ResponseEntity<Object>) adminService.listAllAdminsByDate();

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param admin
	 * @return
	 */
	@PostMapping("/adminRegister")

	public ResponseEntity<Object> adminRegistration(@RequestBody Admin admin) {

		LOG.info("Admin added successfully : " + admin);
		return adminService.saveAdmin(admin);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/viewAdmin")

	public ResponseEntity<Object> viewAdmin() {
		if (isLogin) {
			try {
				Admin admin = adminService.getAdmin(idd);
				LOG.info("Admin found : " + admin);
				return new ResponseEntity<>(admin, HttpStatus.FOUND);
			} catch (NoSuchElementException e) {
				LOG.info("Admin not found");
				return new ResponseEntity<>("Admin not found incorrect id ", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	@GetMapping("/degreeList")

	public ResponseEntity<Object> degreeList() {
		if (isLogin) {
			return (ResponseEntity<Object>) adminService.listAllDegrees();

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
	@GetMapping("/Admin'sAccountVerification")
	public ResponseEntity<Object> verifyAccount(@RequestHeader long id, @RequestHeader int emailToken,
			@RequestHeader int smsToken) {

		try {
			LOG.info("Admin verified ");
			return adminService.verify(id, emailToken, smsToken);

		} catch (NoSuchElementException e) {
			LOG.info("Admin not verified");
			return new ResponseEntity<>("Admin not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param admin
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	@PutMapping("/updateAdmin'sName")

	public ResponseEntity<Object> updateName(@RequestBody Admin admin, @RequestHeader String firstName,
			@RequestHeader String lastName) {

		if (isLogin) {

			try {
				adminService.updateAdminName(adminService.getAdmin(idd), firstName, lastName);
				LOG.info("Admin's name Updated ");
				return new ResponseEntity<>("Admin's name updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Admin not found ");
				return new ResponseEntity<>("Admin not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param admin
	 * @param password
	 * @return
	 */
	@PutMapping("/updateAdmin'sPassword")

	public ResponseEntity<Object> updatePassword(@RequestBody Admin admin, @RequestHeader String password) {

		if (isLogin) {

			try {
				adminService.updateAdminPassword(adminService.getAdmin(idd), password);
				LOG.info("Admin's password updated ");
				return new ResponseEntity<>("Admin's password updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Admin not found  ");
				return new ResponseEntity<>("Admin not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @return
	 */
	@PutMapping("/updateStudentviaAdmin")

	public ResponseEntity<Object> updateStudent(@RequestBody Student student) {

		if (isLogin) {

			try {

				adminService.updateStudent(student);
				LOG.info("Student updated successfully ");
				return new ResponseEntity<>("Student updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Student not found! ");
				return new ResponseEntity<>("Student not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param teacher
	 * @return
	 */
	@PutMapping("/updateTeacherviaAdmin")

	public ResponseEntity<Object> updateTeacher(@RequestBody Teacher teacher) {

		if (isLogin) {

			try {

				adminService.updateTeacher(teacher);
				LOG.info("Teacher updated successfully ");
				return new ResponseEntity<>("Teacher updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Teacher not found ");
				return new ResponseEntity<>("Teacher not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param department
	 * @return
	 */
	@PutMapping("/updateDepartmentviaAdmin")

	public ResponseEntity<Object> updateDepartment(@RequestBody Department department) {

		if (isLogin) {

			try {

				adminService.updateDepartment(department);
				LOG.info("Department updated successfully ");
				return new ResponseEntity<>("Department updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Department not found! ");
				return new ResponseEntity<>("Department not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param curriculum
	 * @return
	 */
	@PutMapping("/updateCurriculumviaAdmin")

	public ResponseEntity<Object> updateCurriculum(@RequestBody Curriculum curriculum) {

		if (isLogin) {

			try {

				adminService.updateCurriculum(curriculum);
				LOG.info("Curriculum updated succesfully ");
				return new ResponseEntity<>("Curriculum updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Curriculum not found! ");
				return new ResponseEntity<>("Curriculum not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 * @return
	 */
	@PutMapping("/updateInstitutionviaAdmin")

	public ResponseEntity<Object> updateInstitution(@RequestBody Institution institution) {

		if (isLogin) {

			try {

				adminService.updateInstitution(institution);
				LOG.info("Institution updated successfully ");
				return new ResponseEntity<>("Institution updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Institution not found ");
				return new ResponseEntity<>("Institution not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param degree
	 * @return
	 */
	@PostMapping("/addDegree")

	public ResponseEntity<String> addDegree(@RequestBody Degree degree) {

		if (isLogin) {
			adminService.saveDegree(degree);
			LOG.info("Degree added successfully " + degree);
			return new ResponseEntity<>("Degree Entered Successfully!", HttpStatus.OK);
		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param degree
	 * @return
	 */

	@PutMapping("/updateDegree")

	public ResponseEntity<Object> updateDegree(@RequestBody Degree degree) {

		if (isLogin) {

			try {

				adminService.updateDegree(degree);
				LOG.info("Degree updated successfully  " + degree);
				return new ResponseEntity<>("Degree updated successfully ", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				LOG.info("Degree not found! ");
				return new ResponseEntity<>("Degree not found incorrect id ", HttpStatus.NOT_FOUND);
			}

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("deleteStudent/{id}")
	public ResponseEntity<Object> hideStudent(@PathVariable Long id) {
		if (isLogin) {
			return adminService.deleteStudent(id);
		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("deleteTeacher/{id}")
	public ResponseEntity<Object> hideTeacher(@PathVariable Long id) {
		if (isLogin) {
			return adminService.deleteTeacher(id);
		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/degreeVerification")
	public ResponseEntity<Object> verifyDegree(@RequestHeader long id) {

		try {

			return adminService.verifyDegree(id);

		} catch (NoSuchElementException e) {

			return new ResponseEntity<>("Degree not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param exam
	 * @return
	 */
	@PostMapping("/addExam")

	public ResponseEntity<String> addExam(@RequestBody Exam exam) {
		if (isLogin) {
			adminService.saveExam(exam);
			LOG.info("Admin Registered ");
			return new ResponseEntity<>("Exam details added successfully!", HttpStatus.OK);
		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @return
	 */
	@GetMapping("/allExam")

	public ResponseEntity<Object> examList() {
		if (isLogin) {

			return (ResponseEntity<Object>) adminService.listAllExams();

		} else
			return new ResponseEntity<>("You are not logged in yet! ", HttpStatus.UNAUTHORIZED);

	}

	/**
	 * @author RaisAhmad
	 * @date 2/11/2021
	 * @param id
	 * @return
	 */
	@PutMapping("/sendToken/{id}")
	public ResponseEntity<Object> sendToken(@RequestHeader long id) {

		try {

			return adminService.sendTokens(id);

		} catch (NoSuchElementException e) {

			return new ResponseEntity<>("Student not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
