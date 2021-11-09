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

import com.example.EducationDepartment.Controller.TeacherController;
import com.example.EducationDepartment.Model.Result;
import com.example.EducationDepartment.Model.Student;
import com.example.EducationDepartment.Model.Teacher;
import com.example.EducationDepartment.Model.ProjectInterface.ResultDTO;
import com.example.EducationDepartment.Model.ProjectInterface.TeacherDTO;
import com.example.EducationDepartment.Repository.ResultRepository;
import com.example.EducationDepartment.Repository.StudentRepository;
import com.example.EducationDepartment.Repository.TeacherRepository;
import com.example.EducationDepartment.Util.Util;

@Service
public class TeacherService {
	private static final Logger LOG = LogManager.getLogger(TeacherService.class);
	private final TeacherRepository teacherRepository;
	private final ResultRepository resultRepository;
	private final StudentRepository studentRepository;
	private final JavaMailSender javaMailSender;
	private final PoliceServiceClient policeServiceClient;

	private final String ACCOUNT_SID = "AC31b2c9f66d33e1256230d66f8eb72516";

	private final String AUTH_TOKEN = "59b0c140cb6508a9942d592a9df496ac";

	private final String FROM_NUMBER = "+14135531059";

	public TeacherService(TeacherRepository teacherRepository, ResultRepository resultRepository,
			JavaMailSender javaMailSender, PoliceServiceClient policeServiceClient, StudentRepository studentRepository) {

		this.teacherRepository = teacherRepository;
		this.resultRepository = resultRepository;
		this.studentRepository = studentRepository;
		this.javaMailSender = javaMailSender;
		this.policeServiceClient = policeServiceClient;
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param teacher
	 * @return
	 */

	public ResponseEntity<Object> saveTeacher(Teacher teacher) {

		try {

			Calendar date = Calendar.getInstance();
			teacher.setDate(date.getTime());
			if (teacher.getFirstName() == null) {
				return new ResponseEntity<>("First name can't be empty", HttpStatus.OK);
			} else if (teacher.getLastName() == null) {
				return new ResponseEntity<>("Last name can't be empty", HttpStatus.OK);
			} else if (teacher.getAddress() == null) {
				return new ResponseEntity<>("Address can't be empty", HttpStatus.OK);
			} else if (teacher.getAge() == 0) {
				return new ResponseEntity<>("Age can't be empty", HttpStatus.OK);
			} else if (teacher.getPassword() == null) {
				return new ResponseEntity<>("Password can't be empty", HttpStatus.OK);
			} else if (teacher.getCnic() == null) {
				return new ResponseEntity<>("CNIC can't be empty", HttpStatus.OK);
			} else if (teacher.getPhone() == null) {
				return new ResponseEntity<>("Phone can't be empty", HttpStatus.OK);
			} else if (teacher.getEmail() == null) {
				return new ResponseEntity<>("E-mail can't be empty", HttpStatus.OK);
			} else if (teacher.getDepartmentName() == null) {
				return new ResponseEntity<>("Department can't be empty", HttpStatus.OK);
			}
			else if (teacher.getDesignation() == null) {
				return new ResponseEntity<>("Designation can't be empty", HttpStatus.OK);
			}else {
				String cnic;
				cnic = teacher.getCnic();
//			boolean status = policeServiceClient.checkCriminalRecord(cnic);
//			if(status == false)
//			{
				teacher.setStatus(false);
				teacherRepository.save(teacher);
				LOG.info("Teacher added successfully : " + teacher);
				return new ResponseEntity<>(
						"Registration performed Successfully! Your registration id is :" + teacher.getId(),
						HttpStatus.OK);
//
//			}
//			else {

				// return new ResponseEntity<>("Registration Failed! You have a criminal
				// record", HttpStatus.OK);
				// }
			}
		} catch (Exception e) {
			LOG.info("Teacher already exist ");
			return new ResponseEntity<>("Teacher already exist at this E-mail Address ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 1/11/2021
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> sendTokens(Long id) {
		Optional<Teacher> teacher = teacherRepository.findById(id);
		try {
			if (teacher.isPresent()) {
				Calendar date = Calendar.getInstance();
				teacher.get().setDate(date.getTime());
				Random rnd = new Random();

				teacher.get().setEmailToken(rnd.nextInt(999999));
				teacher.get().setSmsToken(rnd.nextInt(999999));
				String sms = ("Token: " + teacher.get().getSmsToken());
				Util util = new Util();
				String phone = teacher.get().getPhone();
				util.send(phone, sms);

				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(teacher.get().getEmail(), teacher.get().getEmail());
				String eToken = ("Token: " + teacher.get().getEmailToken());
				msg.setSubject("Your Token");
				msg.setText(eToken);
				javaMailSender.send(msg);
				long timeInSecs = date.getTimeInMillis();
				Date afterAdding3Mins = new Date(timeInSecs + (3 * 60 * 1000));
				teacher.get().setExpirationDate(afterAdding3Mins);

				teacher.get().setStatus(false);
				LOG.info("Token sent successfully");
				return ResponseEntity.ok().body(teacherRepository.save(teacher.get()));

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

	public Teacher getEmail(String email) {
		return teacherRepository.findByEmail(email);
	}

	/**
	 * @author RaisAhmad
	 * @date 4/11/2021
	 * @param result
	 * @return
	 */
	public Object saveResult(Result result) {

		try {

			Calendar date = Calendar.getInstance();

			result.setDate(date.getTime());
			LOG.info("Result added successfully : " + result);
			return ResponseEntity.ok().body(resultRepository.save(result));
		} catch (Exception e) {
			return new ResponseEntity<>("User already exist ", HttpStatus.CONFLICT);
		}

	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param id
	 * @return
	 */

	public Teacher getTeacher(long id) {
		return teacherRepository.findById(id).get();
	}

	public ResponseEntity<Object> getTeacherId(long id) {

		Optional<Teacher> teacher = teacherRepository.findById(id);
		if (teacher.isPresent()) {
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setFirstName(teacher.get().getFirstName());
			teacherDTO.setLastName(teacher.get().getLastName());
			teacherDTO.setCnic(teacher.get().getCnic());
			teacherDTO.setAddress(teacher.get().getAddress());
			teacherDTO.setEmail(teacher.get().getEmail());
			teacherDTO.setAge(teacher.get().getAge());
			teacherDTO.setPhone(teacher.get().getPhone());
			teacherDTO.setDesignation(teacher.get().getDesignation());
			LOG.info("Teacher DTO shown successfully ");
			return ResponseEntity.ok().body(teacherDTO);
		} else
			return new ResponseEntity<>("Teacher not Found ", HttpStatus.BAD_REQUEST);

	}

	public Result getResult(long id) {
		return resultRepository.findById(id).get();
	}
	
	public Student getStudentId(long id) {
		return studentRepository.findById(id).get();
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param result
	 * @return
	 */

	public ResponseEntity<Object> updateStudentMarks(Result result) {
		try {

			Calendar date = Calendar.getInstance();

			result.setDate(date.getTime());
			resultRepository.save(result);
			LOG.info("Result updated successfully : " + result);
			return new ResponseEntity<>("Result has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Result is not Updated", HttpStatus.BAD_REQUEST);
		}
	}
	
		/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param teacher
	 * @param firstName
	 * @param lastName
	 * @return
	 */

	public ResponseEntity<Object> updateTeacherName(Teacher teacher, String firstName, String lastName) {
		try {
			teacher.setFirstName(firstName);
			teacher.setLastName(lastName);
			Calendar date = Calendar.getInstance();
			teacher.setUpdatedDate(date.getTime());
			teacherRepository.save(teacher);
			LOG.info("Teacher name updated successfully : " + teacher);
			return new ResponseEntity<>("Teacher's name has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Teacher is not Updated", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param teacher
	 * @param password
	 * @return
	 */

	public ResponseEntity<Object> updateTeacherPassword(Teacher teacher, String password) {
		try {
			teacher.setPassword(password);
			Calendar date = Calendar.getInstance();
			teacher.setUpdatedDate(date.getTime());

			teacherRepository.save(teacher);
			LOG.info("Teacher password updated successfully : " + teacher);
			return new ResponseEntity<>("Teacher has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Teacher is not Updated", HttpStatus.BAD_REQUEST);
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

	public ResponseEntity<Object> verify(long id, int emailToken, int smsToken) {
		try {
			Teacher teacher = teacherRepository.findByIdAndEmailTokenAndSmsToken(id, emailToken, smsToken);

			Calendar date = Calendar.getInstance();
			if (date.getTime().before(teacher.getExpirationDate())) {
				teacher.setStatus(true);
				System.out.println("Teacher is:  " + teacher.toString());
				teacherRepository.save(teacher);
				LOG.info("Teacher verified successfully : " + teacher);
				return new ResponseEntity<>("Teacher has been successfully Verified", HttpStatus.CREATED);

			} else
				return new ResponseEntity<>("Teacher has not been Verified! Token Expired!", HttpStatus.CREATED);

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Teacher is not Verified ", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author RaisAhmad
	 * @date 29/10/2021
	 * @param teacher
	 * @param phone
	 * @return
	 */

	public ResponseEntity<Object> updateTeacherPhone(Teacher teacher, String phone) {
		try {
			teacher.setPhone(phone);
			Calendar date = Calendar.getInstance();
			teacher.setUpdatedDate(date.getTime());
			teacherRepository.save(teacher);
			LOG.info("Teacher's phone number is updated :  " + teacher);
			return new ResponseEntity<>("Teacher has been successfully Updated", HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			LOG.info("Teacher name is not updated ");
			return new ResponseEntity<>("Teacher is not Updated", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Get criminal status before registration using Feign client
	 * 
	 * @author RaisAhmad
	 * @date 4/11/2021
	 * @return status
	 */

//		public boolean checkCriminalRecord1() {
//			String cnic = "61101-7896541-5";
//
//			boolean status = policeServiceClient.checkCriminalRecord(cnic);
//
//			return status;
//		}

	/**
	 * Third-Party APIs
	 * 
	 * @return
	 */
//	public String bankAPI() {
//
//		String str = policeServiceClient.bankFiegn();
//
//		return str;
//	}
//
//	public ResponseEntity<Object> getAllCurrency() {
//
//		ResponseEntity<Object> data = policeServiceClient.getAllCurrencies();
//
//		return data;
//	}

}
