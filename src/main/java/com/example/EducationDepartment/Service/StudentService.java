package com.example.EducationDepartment.Service;

import java.util.ArrayList;
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
import com.example.EducationDepartment.Model.Degree;
import com.example.EducationDepartment.Model.Exam;
import com.example.EducationDepartment.Model.Institution;
import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Model.ProjectInterface.DegreeDTO;
import com.example.EducationDepartment.Model.ProjectInterface.ExamDto;
import com.example.EducationDepartment.Model.ProjectInterface.InstitutionDTO;
import com.example.EducationDepartment.Model.ProjectInterface.ResultDTO;
import com.example.EducationDepartment.Model.ProjectInterface.StudentDTO;
import com.example.EducationDepartment.Model.ProjectInterface.StudentResultDto;
import com.example.EducationDepartment.Repository.DegreeRepository;
import com.example.EducationDepartment.Repository.StudentRepository;
import com.example.EducationDepartment.Util.Util;
import com.mysql.cj.result.BooleanValueFactory;

@Service
public class StudentService {
	private static final Logger LOG = LogManager.getLogger(StudentService.class);
	private final StudentRepository studentRepository;
	private final DegreeRepository degreeRepository;
	private final JavaMailSender javaMailSender;

	private final String ACCOUNT_SID = "AC31b2c9f66d33e1256230d66f8eb72516";

	private final String AUTH_TOKEN = "59b0c140cb6508a9942d592a9df496ac";

	private final String FROM_NUMBER = "+14135531059";

	public StudentService(StudentRepository studentRepository, JavaMailSender javaMailSender,
			DegreeRepository degreeRepository) {

		this.studentRepository = studentRepository;
		this.degreeRepository = degreeRepository;
		this.javaMailSender = javaMailSender;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @return
	 */

	public ResponseEntity<Object> listAllStudentsByDate() {

		List<Student> studentList = studentRepository.findAllByOrderByDateDesc();
		if (studentList.isEmpty()) {
			return new ResponseEntity<>("No data available", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(studentList, HttpStatus.OK);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param email
	 * @return
	 */
	public Student getEmail(String email) {
		return studentRepository.findByEmail(email);
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @return
	 */

	public ResponseEntity<Object> saveStudent(Student student) {

		try {

			Calendar date = Calendar.getInstance();
			student.setDate(date.getTime());
			if (student.getFirstName() == null) {
				return new ResponseEntity<>("First name can't be empty", HttpStatus.OK);
			} else if (student.getLastName() == null) {
				return new ResponseEntity<>("Last name can't be empty", HttpStatus.OK);
			} else if (student.getAddress() == null) {
				return new ResponseEntity<>("Address can't be empty", HttpStatus.OK);
			} else if (student.getAge() == 0) {
				return new ResponseEntity<>("Age can't be empty", HttpStatus.OK);
			} else if (student.getPassword() == null) {
				return new ResponseEntity<>("Password can't be empty", HttpStatus.OK);
			} else if (student.getCnic() == null) {
				return new ResponseEntity<>("CNIC can't be empty", HttpStatus.OK);
			} else if (student.getPhone() == null) {
				return new ResponseEntity<>("Phone can't be empty", HttpStatus.OK);
			} else if (student.getEmail() == null) {
				return new ResponseEntity<>("E-mail can't be empty", HttpStatus.OK);
			} else {

				student.setStatus(false);

				studentRepository.save(student);
				LOG.info("Student added successfully : " + student);
				return new ResponseEntity<>(
						"Registration performed Successfully! Your registration id is :" + student.getId(),
						HttpStatus.OK);
			}

		} catch (Exception e) {
			LOG.info("Student is not added ");
			return new ResponseEntity<>(
					"Either you are missing any detail or Student already exist at this E-mail Address",
					HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @return
	 */
	public Student getStudent(long id) {
		return studentRepository.findById(id).get();
	}

	public ResponseEntity<Object> getStudentId(long id) {

		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setFirstName(student.get().getFirstName());
			studentDTO.setLastName(student.get().getLastName());
			studentDTO.setCnic(student.get().getCnic());
			studentDTO.setAddress(student.get().getAddress());
			studentDTO.setEmail(student.get().getEmail());
			studentDTO.setAge(student.get().getAge());
			studentDTO.setPhone(student.get().getPhone());

			LOG.info("Student DTO displayed  ");
			return ResponseEntity.ok().body(studentDTO);
		} else
			return new ResponseEntity<>("Student not Found ", HttpStatus.BAD_REQUEST);

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public ResponseEntity<Object> updateStudent(Student student, String firstName, String lastName) {
		try {
			student.setFirstName(firstName);
			student.setLastName(lastName);
			Calendar date = Calendar.getInstance();
			student.setUpdatedDate(date.getTime());
			studentRepository.save(student);
			LOG.info("Student updated successfully : " + student);
			return new ResponseEntity<>("Student has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Student is not updated ");
			return new ResponseEntity<>("Student is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @param password
	 * @return
	 */

	public ResponseEntity<Object> updateStudentPassword(Student student, String password) {
		try {
			student.setPassword(password);
			Calendar date = Calendar.getInstance();
			student.setUpdatedDate(date.getTime());
			studentRepository.save(student);
			LOG.info("Student password is updated :  " + student);
			return new ResponseEntity<>("Student's password has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Student password is not updated ");
			return new ResponseEntity<>("Student password is not Updated", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param student
	 * @param phone
	 * @return
	 */

	public ResponseEntity<Object> updateStudentPhone(Student student, String phone) {
		try {
			student.setPhone(phone);
			Calendar date = Calendar.getInstance();
			student.setUpdatedDate(date.getTime());
			studentRepository.save(student);
			LOG.info("Student's phone number is updated :  " + student);
			return new ResponseEntity<>("Student has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Student name is not updated ");
			return new ResponseEntity<>("Student is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @param emailToken
	 * @param smsToken
	 * @return
	 */

	public ResponseEntity<Object> sendTokens(Long id) {
		Optional<Student> student = studentRepository.findById(id);
		try {
			if (student.isPresent()) {
				Calendar date = Calendar.getInstance();
				student.get().setDate(date.getTime());
				Random rnd = new Random();

				student.get().setEmailToken(rnd.nextInt(999999));
				student.get().setSmsToken(rnd.nextInt(999999));
				String sms = ("Token: " + student.get().getSmsToken());
				Util util = new Util();
				String phone = student.get().getPhone();
				util.send(phone, sms);

				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(student.get().getEmail(), student.get().getEmail());
				String eToken = ("Token: " + student.get().getEmailToken());
				msg.setSubject("Your Token");
				msg.setText(eToken);
				javaMailSender.send(msg);
				long timeInSecs = date.getTimeInMillis();
				Date afterAdding3Mins = new Date(timeInSecs + (3 * 60 * 1000));
				student.get().setExpirationDate(afterAdding3Mins);

				student.get().setStatus(false);
				LOG.info("Tokens sent successfully ");
				return ResponseEntity.ok().body(studentRepository.save(student.get()));

			} else
				return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

		} catch (Exception exception) {
			LOG.info("Tokens can't be sent ");
			return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param id
	 * @param emailToken
	 * @param smsToken
	 * @return
	 */
	public ResponseEntity<Object> verify(long id, int emailToken, int smsToken) {
		try {
			Student student = studentRepository.findByIdAndEmailTokenAndSmsToken(id, emailToken, smsToken);

			Calendar date = Calendar.getInstance();
			if (date.getTime().before(student.getExpirationDate())) {
				student.setStatus(true);
				System.out.println("Student is:  " + student.toString());
				studentRepository.save(student);
				LOG.info("Student verified successfully : " + student);
				return new ResponseEntity<>("Student has been successfully Verified", HttpStatus.CREATED);

			} else
				return new ResponseEntity<>("Student has not been Verified! Token Expired!", HttpStatus.CREATED);

		} catch (NoSuchElementException e) {
			LOG.info("Student can not be verified ");
			return new ResponseEntity<>("Student is not Verified ", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 */

	public ResponseEntity<Object> deleteStudent(long id) {
		try {
			studentRepository.deleteById(id);
			LOG.info("Student deleted successfully ");
			return new ResponseEntity<Object>("Student deleted successfully! ", HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Student can't be deleted ");
			return new ResponseEntity<Object>("Student not found! ", HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Object> getResultByStudentId(long id) {
		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			StudentResultDto studentResultDTO = new StudentResultDto();
			studentResultDTO.setFirstName(student.get().getFirstName());
			studentResultDTO.setLastNamString(student.get().getLastName());
			studentResultDTO.setCnic(student.get().getCnic());
			ResultDTO resultDTO = new ResultDTO();
			List<ResultDTO> resultDTOs = new ArrayList<ResultDTO>();
			Result newResult = new Result();

			for (Result result : student.get().getResult()) {
				resultDTO.setObtainedMarks(result.getObtainedMarks());
				resultDTO.setTotalMarks(result.getTotalMarks());
				resultDTO.setClassAndSec(result.getClassAndSec());
				resultDTOs.add(resultDTO);

			}

			studentResultDTO.setResult(resultDTOs);
			LOG.info("Student's result has been displayed ");
			return ResponseEntity.ok().body(studentResultDTO);
		} else
			return new ResponseEntity<>("Student not Found ", HttpStatus.BAD_REQUEST);
	}

	/**
	 * @author RaisAhmad
	 * @date 4/11/2021
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> verifyDegree(long id) {
		try {
			Optional<Degree> degree = degreeRepository.findById(id);
			if (degree.isPresent()) {
				degree.get().setStatus(true);
				System.out.println("Degree is:  " + degree.toString());
				degreeRepository.save(degree.get());
				LOG.info("Degree has been verified successfully : " + degree);
				return new ResponseEntity<>("Degree has been successfully Verified", HttpStatus.CREATED);

			} else
				return new ResponseEntity<>("Degree has not been Verified!", HttpStatus.CREATED);

		} catch (NoSuchElementException e) {
			LOG.info("Degree could not be verified ");
			return new ResponseEntity<>("Degree is not Verified ", HttpStatus.BAD_REQUEST);
		}
	}

	public boolean verifyCNIC(String cnic) {

		try {

			Optional<Student> student = studentRepository.findByCnic(cnic);

			if (student.isPresent()) {
				LOG.info("CNIC verified ");
				return true;
			} else
				return false;

		} catch (Exception e) {
			LOG.info("CNIC not verified ");
			return false;

		}

	}

	public boolean verifyQualification(String cnic, String degreeName) {

		try {

			Optional<Student> student = studentRepository.findByCnic(cnic);

			if (student.isPresent()) {
				student.get().getDegree();
				for (Degree degree : student.get().getDegree()) {
					if (degree.getName().equals(degreeName)) {
						LOG.info("Qualification verified ");
						return true;
					}
				}
				return false;
			} else
				return false;

		} catch (Exception e) {
			LOG.info("Qualification not been verified ");
			return false;
		}
	}

}
