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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author RaisAhmad
 * @date 29/10/2021
 * @Discription Exam Class
 */

@Entity
@Table(name = "t_exam")
public class Exam {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = true)
	private Date date;
	
	@Column(nullable = true)
	private String discription;

	@Column(nullable = true)
	private Date updatedDate;

	/**
	 * Many To Many Relationship between Exam and Institution
	 */
	
	@ManyToMany(targetEntity = Institution.class, cascade = { CascadeType.MERGE })

	@JoinTable(name = "t_ExamInstitution", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "institutionId") })
	private List<Institution> institution = new ArrayList<>();

	/**
	 * One To Many Relationship between Exam and Result
	 */
	
//	@OneToMany(targetEntity = Result.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//	@JoinColumn(name = "resultId", referencedColumnName = "id")
//	private List<Result> result = new ArrayList<>();
//	
//	
//	public List<Result> getResult() {
//		return result;
//	}
//
//	public void setResult(List<Result> result) {
//		this.result = result;
//	}

	public List<Institution> getInstitution() {
		return institution;
	}

	public void setInstitution(List<Institution> institution) {
		this.institution = institution;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}



}
