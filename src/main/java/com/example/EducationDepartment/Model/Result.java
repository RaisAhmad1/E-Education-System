package com.example.EducationDepartment.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author RaisAhmad
 * @date 29/10/2021
 * @Discription Result Class
 */

@Entity
@Table(name = "t_result")
public class Result {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = true)
	private Date date;

	@Column(nullable = true)
	private long studentId;

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	@Column(nullable = true)
	private String totalMarks;
	
	@Column(nullable = true)
	private String classAndSec;

	@Column(nullable = true)
	private String obtainedMarks;

	@Column(nullable = true)
	private String updatedDate;

	/**
	 * Many To Many Relationship between Exam and Result
	 */
	
	@ManyToMany(targetEntity = Exam.class, cascade = { CascadeType.MERGE })

	@JoinTable(name = "t_resultExam", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "examId") })

	private List<Exam> exam = new ArrayList<>();
	
	

	public List<Exam> getExam() {
		return exam;
	}

	public void setExam(List<Exam> exam) {
		this.exam = exam;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getClassAndSec() {
		return classAndSec;
	}

	public void setClassAndSec(String classAndSec) {
		this.classAndSec = classAndSec;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(String totalMarks) {
		this.totalMarks = totalMarks;
	}

	public String getObtainedMarks() {
		return obtainedMarks;
	}

	public void setObtainedMarks(String obtainedMarks) {
		this.obtainedMarks = obtainedMarks;
	}

}
