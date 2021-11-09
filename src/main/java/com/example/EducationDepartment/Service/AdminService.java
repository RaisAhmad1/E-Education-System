package com.example.EducationDepartment.Service;

import java.util.Calendar;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Admin;
import com.example.EducationDepartment.Model.Curriculum;
import com.example.EducationDepartment.Model.Degree;
import com.example.EducationDepartment.Model.Department;
import com.example.EducationDepartment.Model.Exam;
import com.example.EducationDepartment.Model.Institution;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Model.Teacher;
import com.example.EducationDepartment.Repository.AdminRepository;
import com.example.EducationDepartment.Repository.CurriculumRepository;
import com.example.EducationDepartment.Repository.DegreeRepository;
import com.example.EducationDepartment.Repository.DepartmentRepository;
import com.example.EducationDepartment.Repository.ExamRepository;
import com.example.EducationDepartment.Repository.InstitutionRepository;
import com.example.EducationDepartment.Repository.StudentRepository;
import com.example.EducationDepartment.Repository.TeacherRepository;
import com.example.EducationDepartment.Util.Util;

@Service
public class AdminService {
	private static final Logger LOG = LogManager.getLogger(AdminService.class);
	private final AdminRepository adminRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final DepartmentRepository departmentRepository;
	private final InstitutionRepository institutionRepository;
	private final CurriculumRepository curriculumRepository;
	private final DegreeRepository degreeRepository;
	private final ExamRepository examRepository;
	private final JavaMailSender javaMailSender;

	private final String ACCOUNT_SID = "AC31b2c9f66d33e1256230d66f8eb72516";

	private final String AUTH_TOKEN = "59b0c140cb6508a9942d592a9df496ac";

	private final String FROM_NUMBER = "+14135531059";

	public AdminService(AdminRepository adminRepository, StudentRepository studentRepository,
			TeacherRepository teacherRepository, JavaMailSender javaMailSender,
			DepartmentRepository departmentRepository, InstitutionRepository institutionRepository,
			CurriculumRepository curriculumRepository, DegreeRepository degreeRepository,
			ExamRepository examRepository) {

		this.adminRepository = adminRepository;
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
		this.departmentRepository = departmentRepository;
		this.institutionRepository = institutionRepository;
		this.curriculumRepository = curriculumRepository;
		this.degreeRepository = degreeRepository;
		this.examRepository = examRepository;
		this.javaMailSender = javaMailSender;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param admin
	 * @return
	 */

	public ResponseEntity<Object> saveAdmin(Admin admin) {

		try {

			Calendar date = Calendar.getInstance();
			admin.setDate(date.getTime());
			if (admin.getFirstName() == null) {
				return new ResponseEntity<>("First name can't be empty", HttpStatus.OK);
			} else if (admin.getLastName() == null) {
				return new ResponseEntity<>("Last name can't be empty", HttpStatus.OK);
			} else if (admin.getAddress() == null) {
				return new ResponseEntity<>("Address can't be empty", HttpStatus.OK);
			} else if (admin.getAge() == 0) {
				return new ResponseEntity<>("Age can't be empty", HttpStatus.OK);
			} else if (admin.getPassword() == null) {
				return new ResponseEntity<>("Password can't be empty", HttpStatus.OK);
			} else if (admin.getCnic() == null) {
				return new ResponseEntity<>("CNIC can't be empty", HttpStatus.OK);
			} else if (admin.getPhone() == null) {
				return new ResponseEntity<>("Phone can't be empty", HttpStatus.OK);
			} else if (admin.getEmail() == null) {
				return new ResponseEntity<>("E-mail can't be empty", HttpStatus.OK);
			} else {
				admin.setStatus(false);

				adminRepository.save(admin);
				LOG.info("Admin added successfully : " + admin);
				return new ResponseEntity<>(
						"Registration performed Successfully! Your registration id is :" + admin.getId(),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Admin already exist at this E-mail Address ", HttpStatus.CONFLICT);
		}

	}

	public ResponseEntity<Object> sendTokens(Long id) {
		Optional<Admin> admin = adminRepository.findById(id);
		try {
			if (admin.isPresent()) {
				Calendar date = Calendar.getInstance();
				admin.get().setDate(date.getTime());
				Random rnd = new Random();

				admin.get().setEmailToken(rnd.nextInt(999999));
				admin.get().setSmsToken(rnd.nextInt(999999));
				String sms = ("Token: " + admin.get().getSmsToken());
				Util util = new Util();
				String phone = admin.get().getPhone();
				util.send(phone, sms);

				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(admin.get().getEmail(), admin.get().getEmail());
				String eToken = ("Token: " + admin.get().getEmailToken());
				msg.setSubject("Your Token");
				msg.setText(eToken);
				javaMailSender.send(msg);
				long timeInSecs = date.getTimeInMillis();
				Date afterAdding3Mins = new Date(timeInSecs + (3 * 60 * 1000));
				admin.get().setExpirationDate(afterAdding3Mins);

				admin.get().setStatus(false);
				LOG.info("Tokens sent successfully : " + admin);
				return ResponseEntity.ok().body(adminRepository.save(admin.get()));

			} else
				return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

		} catch (Exception exception) {
			return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param email
	 * @return
	 */
	public Admin getEmail(String email) {
		return adminRepository.findByEmail(email);
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public ResponseEntity<Object> listAllTeachersByDate() {
		List<Teacher> teacherList = teacherRepository.findAllByOrderByDateDesc();
		if (teacherList.isEmpty()) {
			LOG.info("Teacher List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("Teacher List : " + teacherList);
			return new ResponseEntity<>(teacherList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public ResponseEntity<Object> listAllDegrees() {
		List<Degree> degreeList = degreeRepository.findAll();
		if (degreeList.isEmpty()) {
			LOG.info("Degree List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("Degree List : " + degreeList);
			return new ResponseEntity<>(degreeList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public ResponseEntity<Object> listAllVerifiedStudents() {
		List<Student> studentList = studentRepository.findAllByStatus(true);
		if (studentList.isEmpty()) {
			LOG.info("Student List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("Student List : " + studentList);
			return new ResponseEntity<>(studentList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public ResponseEntity<Object> listAllVerifiedTeachers() {
		List<Teacher> teacherList = teacherRepository.findAllByStatus(true);
		if (teacherList.isEmpty()) {
			LOG.info("Teacher list is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("Teacher List : " + teacherList);
			return new ResponseEntity<>(teacherList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */
	public ResponseEntity<Object> listAllStudentsByDate() {
		List<Student> studentList = studentRepository.findAllByOrderByDateDesc();
		if (studentList.isEmpty()) {
			LOG.info("Student List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("Student List : " + studentList);
			return new ResponseEntity<>(studentList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	public ResponseEntity<Object> listAllAdminsByDate() {
		List<Admin> adminList = adminRepository.findAllByOrderByDateDesc();
		if (adminList.isEmpty()) {
			LOG.info("Admin List is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("Admin List : " + adminList);
			return new ResponseEntity<>(adminList, HttpStatus.OK);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @return
	 */
	public Admin getAdmin(long id) {
		return adminRepository.findById(id).get();
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @param emailToken
	 * @param smsToken
	 * @return
	 */

	public ResponseEntity<Object> verify(long id, int emailToken, int smsToken) {
		try {
			Admin admin = adminRepository.findByIdAndEmailTokenAndSmsToken(id, emailToken, smsToken);

			Calendar date = Calendar.getInstance();
			if (date.getTime().before(admin.getExpirationDate())) {
				admin.setStatus(true);
				System.out.println("Admin is:  " + admin.toString());
				adminRepository.save(admin);
				LOG.info("Admin verified successfully ");
				return new ResponseEntity<>("Admin has been successfully Verified", HttpStatus.CREATED);

			} else
				return new ResponseEntity<>("Admin has not been Verified! Token Expired!", HttpStatus.CREATED);

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Admin is not Verified ", HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<Object> updateAdminName(Admin admin, String firstName, String lastName) {
		try {
			admin.setFirstName(firstName);
			admin.setLastName(lastName);
			Calendar date = Calendar.getInstance();
			admin.setDate(date.getTime());
			adminRepository.save(admin);
			LOG.info("Admin name updated successfully : " + admin);
			return new ResponseEntity<>("Admin has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Admin not updated ");
			return new ResponseEntity<>("Admin is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param admin
	 * @param password
	 * @return
	 */
	public ResponseEntity<Object> updateAdminPassword(Admin admin, String password) {
		try {
			admin.setPassword(password);
			Calendar date = Calendar.getInstance();
			admin.setUpdatedDate(date.getTime());
			adminRepository.save(admin);
			LOG.info("Admin password updated successfully : " + admin);
			return new ResponseEntity<>("Admin has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Admin not updated ");
			return new ResponseEntity<>("Admin is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @return
	 */

	public ResponseEntity<Object> updateStudent(Student student) {
		try {
			Calendar date = Calendar.getInstance();
			student.setUpdatedDate(date.getTime());
			studentRepository.save(student);
			LOG.info("Student updated successfully : " + student);
			return new ResponseEntity<>("Student has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Student not updated ");
			return new ResponseEntity<>("Student is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param teacher
	 * @return
	 */

	public ResponseEntity<Object> updateTeacher(Teacher teacher) {
		try {
			Calendar date = Calendar.getInstance();
			teacher.setUpdatedDate(date.getTime());
			teacherRepository.save(teacher);
			LOG.info("Teacher updated successfully : " + teacher);
			return new ResponseEntity<>("Teacher has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Teacher not updated  ");
			return new ResponseEntity<>("Teacher is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param department
	 * @return
	 */
	public ResponseEntity<Object> updateDepartment(Department department) {
		try {
			Calendar date = Calendar.getInstance();
			department.setUpdatedDate(date.getTime());
			departmentRepository.save(department);
			LOG.info("Department updated successfully : " + department);
			return new ResponseEntity<>("Department has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Department not updated ");
			return new ResponseEntity<>("Department is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param curriculum
	 * @return
	 */
	public ResponseEntity<Object> updateCurriculum(Curriculum curriculum) {
		try {
			Calendar date = Calendar.getInstance();
			curriculum.setUpdatedDate(date.getTime());
			curriculumRepository.save(curriculum);
			LOG.info("Curriculum updated successfully : " + curriculum);
			return new ResponseEntity<>("Curriculum has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Curriculum not updated ");
			return new ResponseEntity<>("Curriculum is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param institution
	 * @return
	 */
	public ResponseEntity<Object> updateInstitution(Institution institution) {
		try {
			Calendar date = Calendar.getInstance();
			institution.setUpdatedDate(date.getTime());

			institutionRepository.save(institution);
			LOG.info("Institution updated successfully : " + institution);
			return new ResponseEntity<>("Institution has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Institution not updated ");
			return new ResponseEntity<>("Institution is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param degree
	 * @return
	 */
	public ResponseEntity<Object> saveDegree(Degree degree) {

		try {

			Calendar date = Calendar.getInstance();
			degree.setDate(date.getTime());
			degree.setStatus(false);
			LOG.info("Degree added successfully : " + degree);
			return ResponseEntity.ok().body(degreeRepository.save(degree));
		} catch (Exception e) {
			LOG.info("Degree is not added ");
			return new ResponseEntity<>("User already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param degree
	 * @return
	 */
	public ResponseEntity<Object> updateDegree(Degree degree) {
		try {
			Calendar date = Calendar.getInstance();
			degree.setUpdatedDate(date.getTime());
			degreeRepository.save(degree);
			LOG.info("Degree updated successfully : " + degree);
			return new ResponseEntity<>("Degree has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Degree is not updated ");
			return new ResponseEntity<>("Degree is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param id
	 * @return
	 */

	public ResponseEntity<Object> deleteTeacher(long id) {
		try {
			Optional<Teacher> teacher = teacherRepository.findById(id);
			if (teacher.isPresent()) {

				teacher.get().setStatus(false);

				Calendar date = Calendar.getInstance();

				teacher.get().setUpdatedDate(date.getTime());
				teacherRepository.save(teacher.get());
				LOG.info("Teacher deleted successfully  ");
				return new ResponseEntity<>("Teacher deleted successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("Teacher does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.info("Teacher could not be deleted ");
			return new ResponseEntity<>("Teacher could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param id
	 * @return
	 */

	public ResponseEntity<Object> deleteStudent(long id) {
		try {
			Optional<Student> student = studentRepository.findById(id);
			if (student.isPresent()) {

				student.get().setStatus(false);

				Calendar date = Calendar.getInstance();

				student.get().setUpdatedDate(date.getTime());
				studentRepository.save(student.get());
				LOG.info("Student deleted successfully  ");
				return new ResponseEntity<>("student deleted successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("student does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.info("Student could not be deleted ");
			return new ResponseEntity<>("student could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	public ResponseEntity<Object> verifyDegree(long id) {
		try {
			Optional<Degree> degree = degreeRepository.findById(id);
			if (degree.isPresent()) {
				degree.get().setStatus(true);
				System.out.println("Degree is:  " + degree.toString());
				degreeRepository.save(degree.get());
				LOG.info("Degree verified successfully : " + degree);
				return new ResponseEntity<>("Degree has been successfully Verified", HttpStatus.CREATED);

			} else
				return new ResponseEntity<>("Degree has not been Verified!", HttpStatus.CREATED);

		} catch (NoSuchElementException e) {
			LOG.info("Degree is not verified ");
			return new ResponseEntity<>("Degree is not Verified ", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param exam
	 * @return
	 */
	public Object saveExam(Exam exam) {

		try {

			Calendar date = Calendar.getInstance();
			exam.setDate(date.getTime());
			LOG.info("Exam added successfully : " + exam);
			return ResponseEntity.ok().body(examRepository.save(exam));
		} catch (Exception e) {
			LOG.info("Exam is not added ");
			return new ResponseEntity<>("User already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @return
	 */
	public ResponseEntity<Object> listAllExams() {
		List<Exam> examList = examRepository.findAll();
		if (examList.isEmpty()) {
			LOG.info("Exam list is empty ");
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			LOG.info("Exam list : " + examList);
			return new ResponseEntity<>(examList, HttpStatus.OK);
		}
	}

}
